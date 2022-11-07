package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Emotion
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CommentLabelingVM"

@HiltViewModel
class CommentLabelingViewModel @Inject constructor() : ViewModel() {
    @Suppress("MaxLineLength")
    private val _comments = listOf(
        Comment(text = "im feeling rather rotten so im not very ambitious right now"),
        Comment(text = "im updating my blog because i feel shitty"),
        Comment(text = "i never make her separate from me because i don t ever want her to feel like i m ashamed with her"),
        Comment(text = "i left with my bouquet of red and yellow tulips under my arm feeling slightly more optimistic than when i arrived"),
        Comment(text = "i was feeling a little vain when i did this one"),
//        Comment(text = "i cant walk into a shop anywhere where i do not feel uncomfortable"),
//        Comment(text = "i felt anger when at the end of a telephone call"),
//        Comment(text = "i explain why i clung to a relationship with a boy who was in many ways immature and uncommitted despite the excitement i should have been feeling for getting accepted into the masters program at the university of virginia"),
//        Comment(text = "i like to have the same breathless feeling as a reader eager to see what will happen next"),
//        Comment(text = "i jest i feel grumpy tired and pre menstrual which i probably am but then again its only been a week and im about as fit as a walrus on vacation for the summer"),
    )

    private val _viewState: MutableStateFlow<CommentLabelingViewState> = MutableStateFlow(
        value = CommentLabelingViewState.Loading
    )
    val viewState: StateFlow<CommentLabelingViewState> = _viewState

    init {
        viewModelScope.launch {
            loadComments()
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

    fun goToNextComment() {
        viewModelScope.launch {
            _viewState.update { state ->
                (state as? CommentLabelingViewState.Active)?.let {
                    if (it.currentCommentIndex + 1 != it.comments.size) {
                        it.copy(
                            currentCommentIndex = it.currentCommentIndex + 1
                        )
                    } else {
                        launch { loadComments() }
                        CommentLabelingViewState.Loading
                    }
                } ?: state
            }
        }
    }

    private suspend fun loadComments() = coroutineScope {
        @Suppress("MagicNumber")
        delay(5000)
        _viewState.update {
            CommentLabelingViewState.Active(
                comments = _comments,
                currentCommentIndex = 0,
            )
        }
        Log.d(TAG, "View state update to ${_viewState.value.type.name}")
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
