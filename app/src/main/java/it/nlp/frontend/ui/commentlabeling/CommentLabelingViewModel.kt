package it.nlp.frontend.ui.commentlabeling

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.nlp.frontend.R
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.Emotion
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetTextsToLabelUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.SaveLabeledTextsUseCase
import it.nlp.frontend.domain.token.usecase.ClearTokenUseCase
import it.nlp.frontend.ui.commentlabeling.model.UiComment
import it.nlp.frontend.ui.commentlabeling.model.UiEmotion
import it.nlp.frontend.ui.core.helpers.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentLabelingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTextsToLabelUseCase: GetTextsToLabelUseCase,
    private val saveLabeledTextsUseCase: SaveLabeledTextsUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<CommentLabelingViewState> = MutableStateFlow(
        value = CommentLabelingViewState.Loading(UiText.ResourceText(R.string.loadingComments))
    )
    val viewState: StateFlow<CommentLabelingViewState> = _viewState

    private val commentsQuantity: Int = checkNotNull(savedStateHandle["commentQuantity"])

    init {
        viewModelScope.launch {
            loadComments(quantity = commentsQuantity)
        }
    }

    fun onEmotionSelected(emotion: UiEmotion) {
        (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
            _viewState.value = CommentLabelingViewState.Loaded.Active(
                comments = it.comments.withChangedEmotionAtIndex(
                    emotion = emotion,
                    index = it.currentCommentIndex
                ),
                currentCommentIndex = it.currentCommentIndex,
            )
        }
    }

    fun goToPreviousComment() {
        (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
            if (it.currentCommentIndex > 0) {
                _viewState.value = CommentLabelingViewState.Loaded.Active(
                    comments = it.comments,
                    currentCommentIndex = it.currentCommentIndex - 1
                )
            }
        }
    }

    fun goToNextComment() {
        (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
            if (it.currentCommentIndex + 1 != it.comments.size) {
                _viewState.value = CommentLabelingViewState.Loaded.Active(
                    comments = it.comments,
                    currentCommentIndex = it.currentCommentIndex + 1
                )
            } else {
                viewModelScope.launch {
                    postComments(it.comments)

                    // Do not load comments if post was unsuccessful
                    if (_viewState.value is CommentLabelingViewState.Loading) {
                        loadComments(quantity = commentsQuantity)
                    }
                }
            }
        }
    }

    fun checkForUnspecifiableEmotionAndGoToNextComment() {
        (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
            val selectedCommentUnspecifiable = it.currentComment.emotion == UiEmotion.Unspecifiable
            if (selectedCommentUnspecifiable) {
                _viewState.value =
                    CommentLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested(
                        comments = it.comments,
                        currentCommentIndex = it.currentCommentIndex,
                    )
            } else {
                goToNextComment()
            }
        }
    }

    fun closeDialog() {
        (_viewState.value as? CommentLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested)?.let {
            _viewState.value = CommentLabelingViewState.Loaded.Active(
                comments = it.comments,
                currentCommentIndex = it.currentCommentIndex,
            )
        }
    }

    fun retryLoading() {
        viewModelScope.launch {
            loadComments(quantity = commentsQuantity)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }

    private suspend fun loadComments(quantity: Int) {
        _viewState.value = CommentLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.loadingComments)
        )

        val getCommentsToLabelResult = getTextsToLabelUseCase(quantity)
        handleGetCommentsToLabelResult(getCommentsToLabelResult)
    }

    private suspend fun postComments(comments: List<UiComment>) {
        _viewState.value = CommentLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.postingComments)
        )

        val domainComments = comments.map { it.toDomainComment() }
        val saveLabeledCommentsResult = saveLabeledTextsUseCase(domainComments)
        handleSaveLabeledCommentsResult(
            comments = comments,
            result = saveLabeledCommentsResult
        )
    }

    private fun handleSaveLabeledCommentsResult(
        comments: List<UiComment>,
        result: SaveLabeledTextsResult,
    ) {
        _viewState.value = when (result) {
            is SaveLabeledTextsResult.Success -> _viewState.value
            is SaveLabeledTextsResult.Failure.NoToken,
            is SaveLabeledTextsResult.Failure.ReadingToken,
            is SaveLabeledTextsResult.Failure.UnauthorizedUser,
            -> CommentLabelingViewState.AuthError(
                errorMessage = UiText.ResourceText(R.string.logInAndTryAgainErrorMessage)
            )
            is SaveLabeledTextsResult.Failure.ServiceUnavailable ->
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.serviceUnavailableShortErrorMessage)
                )
            is SaveLabeledTextsResult.Failure.Network -> CommentLabelingViewState.Loaded.CommentPostingError(
                comments = comments,
                currentCommentIndex = comments.lastIndex,
                errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
            )
            is SaveLabeledTextsResult.Failure.NonLabeledTexts ->
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.commentsNotLabeledErrorMessage)
                )
            is SaveLabeledTextsResult.Failure.WrongEmotionParsing ->
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            is SaveLabeledTextsResult.Failure.AlreadyAssignedByThisUser ->
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            is SaveLabeledTextsResult.Failure.Unknown -> CommentLabelingViewState.Loaded.CommentPostingError(
                comments = comments,
                currentCommentIndex = comments.lastIndex,
                errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
            )
        }
    }

    private fun handleGetCommentsToLabelResult(result: GetTextsToLabelResult) {
        _viewState.value = when (result) {
            is GetTextsToLabelResult.Success -> CommentLabelingViewState.Loaded.Active(
                comments = result.emotionTexts.map { it.toUiComment() },
                currentCommentIndex = 0,
            )
            is GetTextsToLabelResult.Failure.NoToken,
            is GetTextsToLabelResult.Failure.ReadingToken,
            is GetTextsToLabelResult.Failure.UnauthorizedUser,
            -> CommentLabelingViewState.AuthError(
                errorMessage = UiText.ResourceText(R.string.logInAndTryAgainErrorMessage)
            )
            is GetTextsToLabelResult.Failure.ServiceUnavailable -> CommentLabelingViewState.CommentLoadingError(
                errorMessage = UiText.ResourceText(R.string.serviceUnavailableLongErrorMessage)
            )
            is GetTextsToLabelResult.Failure.Network -> CommentLabelingViewState.CommentLoadingError(
                errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
            )
            is GetTextsToLabelResult.Failure.NoTexts -> CommentLabelingViewState.CommentLoadingError(
                errorMessage = UiText.ResourceText(R.string.noMoreCommentsToLabelError)
            )
            is GetTextsToLabelResult.Failure.TextsNumberOutOfRange ->
                CommentLabelingViewState.CommentLoadingError(
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            is GetTextsToLabelResult.Failure.Unknown -> CommentLabelingViewState.CommentLoadingError(
                errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
            )
        }
    }
}

fun List<UiComment>.withChangedEmotionAtIndex(
    emotion: UiEmotion,
    index: Int,
): List<UiComment> {
    val newList = this.toMutableList()
    newList[index] = UiComment(
        id = this[index].id,
        text = this[index].text,
        emotion = emotion,
    )
    return newList
}

fun UiComment.toDomainComment() =
    EmotionText(
        id = id,
        text = text.value,
        emotion = emotion?.name?.let { Emotion.valueOf(it) },
    )

fun EmotionText.toUiComment() = UiComment(
    id = id,
    text = UiText.StringText(text),
    emotion = emotion?.name?.let { UiEmotion.valueOf(it) },
)
