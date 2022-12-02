package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.R
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiComment
import it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model.UiEmotion
import it.winter2223.bachelor.ak.frontend.ui.core.helpers.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentLabelingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCommentsToLabelUseCase: GetCommentsToLabelUseCase,
    private val saveLabeledCommentsUseCase: SaveLabeledCommentsUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
    companion object {
        private const val TAG = "CommentLabelingVM"
    }

    private val _viewState: MutableStateFlow<CommentLabelingViewState> = MutableStateFlow(
        value = CommentLabelingViewState.Loading(UiText.StringText("loading"))
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

        val getCommentsToLabelResult = getCommentsToLabelUseCase(quantity)
        handleGetCommentsToLabelResult(getCommentsToLabelResult)
    }

    private suspend fun postComments(comments: List<UiComment>) {
        _viewState.value = CommentLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.postingComments)
        )

        val domainComments = comments.map { it.toDomainComment() }
        val saveLabeledCommentsResult = saveLabeledCommentsUseCase(domainComments)
        handleSaveLabeledCommentsResult(
            comments = comments,
            result = saveLabeledCommentsResult
        )
    }

    private fun handleSaveLabeledCommentsResult(
        comments: List<UiComment>,
        result: SaveLabeledCommentsResult,
    ) {
        _viewState.value = when (result) {
            is SaveLabeledCommentsResult.Success -> {
                _viewState.value
            }
            is SaveLabeledCommentsResult.Failure.NoToken,
            is SaveLabeledCommentsResult.Failure.ReadingToken,
            -> {
                Log.e(TAG, "User can't be authorized. Logging in is required")
                CommentLabelingViewState.AuthError
            }
            is SaveLabeledCommentsResult.Failure.Network -> {
                Log.e(TAG, "Posting comments failed because the server is not accessible")
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
                )
            }
            is SaveLabeledCommentsResult.Failure.NonLabeledComments -> {
                Log.e(TAG, "Posting comments failed because some comments did not have emotions assigned")
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.commentsNotLabeledErrorMessage)
                )
            }
            is SaveLabeledCommentsResult.Failure.WrongEmotionParsing -> {
                Log.e(TAG, "Posting comments failed because some emotions are parsed incorrectly")
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            }
            is SaveLabeledCommentsResult.Failure.AlreadyAssignedByThisUser -> {
                Log.e(TAG, "Posting comments failed because they are already labeled by this user")
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            }
            is SaveLabeledCommentsResult.Failure.Unknown -> {
                Log.e(TAG, "Posting comments failed due to unknown error")
                CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
                )
            }
        }
    }

    private fun handleGetCommentsToLabelResult(result: GetCommentsToLabelResult) {
        _viewState.value = when (result) {
            is GetCommentsToLabelResult.Success -> {
                val uiComments = result.comments.map { it.toUiComment() }
                CommentLabelingViewState.Loaded.Active(
                    comments = uiComments,
                    currentCommentIndex = 0,
                )
            }
            is GetCommentsToLabelResult.Failure.NoToken,
            is GetCommentsToLabelResult.Failure.ReadingToken,
            -> {
                Log.e(TAG, "User can't be authorized. Logging in is required")
                CommentLabelingViewState.AuthError
            }
            is GetCommentsToLabelResult.Failure.Network -> {
                Log.e(TAG, "Loading comments failed because the server is not accessible")
                CommentLabelingViewState.CommentLoadingError(
                    errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
                )
            }
            is GetCommentsToLabelResult.Failure.NoComments -> {
                Log.e(TAG, "Loading comments failed because there are no comments for this user to label")
                CommentLabelingViewState.CommentLoadingError(
                    errorMessage = UiText.ResourceText(R.string.noMoreCommentsToLabelError)
                )
            }
            is GetCommentsToLabelResult.Failure.CommentsNumberOutOfRange -> {
                Log.e(TAG, "Loading comments failed because there are no comments for this user to label")
                CommentLabelingViewState.CommentLoadingError(
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )
            }
            is GetCommentsToLabelResult.Failure.Unknown -> {
                Log.e(TAG, "Loading comments failed due to an unknown error")
                CommentLabelingViewState.CommentLoadingError(
                    errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
                )
            }
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
    Comment(
        id = id,
        text = text.value,
        emotion = emotion?.name?.let { Emotion.valueOf(it) },
    )

fun Comment.toUiComment() = UiComment(
    id = id,
    text = UiText.StringText(text),
    emotion = emotion?.name?.let { UiEmotion.valueOf(it) },
)
