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
        viewModelScope.launch {
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
    }

    fun goToPreviousComment() {
        viewModelScope.launch {
            (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
                if (it.currentCommentIndex > 0) {
                    _viewState.value = CommentLabelingViewState.Loaded.Active(
                        comments = it.comments,
                        currentCommentIndex = it.currentCommentIndex - 1
                    )
                }
            }
        }
    }

    fun goToNextComment() {
        viewModelScope.launch {
            (_viewState.value as? CommentLabelingViewState.Loaded)?.let {
                if (it.currentCommentIndex + 1 != it.comments.size) {
                    _viewState.value = CommentLabelingViewState.Loaded.Active(
                        comments = it.comments,
                        currentCommentIndex = it.currentCommentIndex + 1
                    )
                } else {
                    postComments(it.comments)

                    // Do not load comments if post was unsuccessful
                    if (_viewState.value !is CommentLabelingViewState.Loaded.CommentPostingError) {
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
                _viewState.value = CommentLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested(
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

    private suspend fun loadComments(quantity: Int) {
        _viewState.value = CommentLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.loadingComments)
        )

        when (val getCommentsToLabelResult = getCommentsToLabelUseCase(quantity)) {
            is GetCommentsToLabelResult.Success -> {
                val uiComments = getCommentsToLabelResult.comments.map { it.toUiComment() }
                _viewState.value = CommentLabelingViewState.Loaded.Active(
                    comments = uiComments,
                    currentCommentIndex = 0,
                )
            }
            is GetCommentsToLabelResult.Failure -> {
                Log.e(TAG, "Loading comments failed")
                _viewState.value = CommentLabelingViewState.CommentLoadingError
            }
        }
    }

    private suspend fun postComments(comments: List<UiComment>) {
        _viewState.value = CommentLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.postingComments)
        )

        val domainComments = comments.map { it.toDomainComment() }
        when (saveLabeledCommentsUseCase(domainComments)) {
            is SaveLabeledCommentsResult.Success -> {
                Log.d(TAG, "Comments posted")
            }
            is SaveLabeledCommentsResult.Failure.NonLabeledComments -> {
                Log.e(
                    TAG,
                    "Posting comments failed because some comments did not have emotions assigned",
                )
                _viewState.value = CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.commentsNotLabeledErrorMessage)
                )
            }
            is SaveLabeledCommentsResult.Failure.Unknown -> {
                Log.e(TAG, "Posting comments failed due to unknown error")
                _viewState.value = CommentLabelingViewState.Loaded.CommentPostingError(
                    comments = comments,
                    currentCommentIndex = comments.lastIndex,
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
