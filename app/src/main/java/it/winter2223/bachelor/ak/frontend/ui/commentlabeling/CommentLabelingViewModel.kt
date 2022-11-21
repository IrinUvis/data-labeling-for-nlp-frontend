package it.winter2223.bachelor.ak.frontend.ui.commentlabeling

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Comment
import it.winter2223.bachelor.ak.frontend.domain.comments.model.Emotion
import it.winter2223.bachelor.ak.frontend.data.comments.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.domain.comments.model.GetCommentsToLabelResult
import it.winter2223.bachelor.ak.frontend.domain.comments.model.SaveLabeledCommentsResult
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
        value = CommentLabelingViewState.Loading
    )
    val viewState: StateFlow<CommentLabelingViewState> = _viewState

    private val commentsQuantity: Int = checkNotNull(savedStateHandle["commentQuantity"])

    init {
        viewModelScope.launch {
            loadComments(quantity = commentsQuantity)
        }
    }

    fun onEmotionSelected(emotion: Emotion) {
        viewModelScope.launch {
            _viewState.update { state ->
                (state as? CommentLabelingViewState.Active)?.let {
                    it.copy(
                        comments = it.comments.withChangedEmotionAtIndex(
                            emotion = emotion,
                            index = it.currentCommentIndex
                        )
                    )
                } ?: state
            }
        }
    }

    fun goToPreviousComment() {
        viewModelScope.launch {
            _viewState.update { state ->
                (state as? CommentLabelingViewState.Active)?.let {
                    if (it.currentCommentIndex > 0) {
                        it.copy(
                            currentCommentIndex = it.currentCommentIndex - 1
                        )
                    } else {
                        it
                    }
                } ?: state
            }
        }
    }

    fun goToNextComment() {
        viewModelScope.launch {
            _viewState.update { state ->
                (state as? CommentLabelingViewState.Active)?.let {
                    if (it.currentCommentIndex + 1 != it.comments.size) {
                        it.copy(
                            currentCommentIndex = it.currentCommentIndex + 1
                        )
                    } else {
                        launch {
                            postComments(state.comments)
                        }
                        launch {
                            loadComments(quantity = commentsQuantity)
                        }
                        CommentLabelingViewState.Loading
                    }
                } ?: state
            }
        }
    }

    private suspend fun loadComments(quantity: Int) {
        when (val getCommentsToLabelResult = getCommentsToLabelUseCase(quantity)) {
            is GetCommentsToLabelResult.Success -> {
                _viewState.update {
                    CommentLabelingViewState.Active(
                        comments = getCommentsToLabelResult.comments,
                        currentCommentIndex = 0,
                    )
                }
            }
            is GetCommentsToLabelResult.Failure -> {
                Log.e(TAG, "Loading comments failed")
                // TODO: Update ui accordingly
            }
        }
    }

    private suspend fun postComments(comments: List<Comment>) {
        when (saveLabeledCommentsUseCase(comments)) {
            is SaveLabeledCommentsResult.Success -> {
                Log.d(TAG, "Comments posted")
            }
            is SaveLabeledCommentsResult.Failure.NonLabeledComments -> {
                Log.e(
                    TAG,
                    "Posting comments failed because some comments did not have emotions assigned",
                )
                // TODO: Update ui accordingly
            }
            is SaveLabeledCommentsResult.Failure.Unknown -> {
                Log.e(TAG, "Posting comments failed due to unknown error")
                // TODO: Update ui accordingly
            }
        }
    }
}

fun List<Comment>.withChangedEmotionAtIndex(
    emotion: Emotion,
    index: Int,
): List<Comment> {
    val newList = this.toMutableList()
    newList[index] = Comment(
        text = this[index].text,
        emotion = emotion,
    )
    return newList
}
