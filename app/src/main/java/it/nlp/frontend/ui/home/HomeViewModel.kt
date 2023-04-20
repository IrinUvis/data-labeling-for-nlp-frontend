package it.nlp.frontend.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val INITIAL_NUMBER_OF_TEXTS = 10
    }

    private val _viewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(
            HomeViewState.Loaded(
                numberOfTextsToLabel = INITIAL_NUMBER_OF_TEXTS,
            )
        )
    val viewState: StateFlow<HomeViewState> = _viewState

    fun updateNumberOfTextsToLabel(newNumber: Int) {
        _viewState.value = HomeViewState.Loaded(
            numberOfTextsToLabel = newNumber
        )
    }
}
