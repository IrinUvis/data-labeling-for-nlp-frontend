package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model.Comment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentLabelingViewModel : ViewModel() {
    private val _comments = setOf(
        Comment(text = "A", emotion = null)
    )

    private val _viewState: MutableStateFlow<CommentLabelingViewState> = MutableStateFlow(
        value = CommentLabelingViewState.Loading
    )
    val viewState: StateFlow<CommentLabelingViewState> = _viewState

    init {
        viewModelScope.launch {
            @Suppress("MagicNumber")
            delay(5000)
            _viewState.value = CommentLabelingViewState.Active(
                commentSet = _comments
            )
        }
    }
}
