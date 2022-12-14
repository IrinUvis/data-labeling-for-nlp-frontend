package it.winter2223.bachelor.ak.frontend.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val INITIAL_NUMBER_OF_COMMENTS = 10
    }

    private val _viewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(
            HomeViewState.Loaded(
                numberOfCommentsToLabel = INITIAL_NUMBER_OF_COMMENTS,
            )
        )
    val viewState: StateFlow<HomeViewState> = _viewState

    fun updateNumberOfCommentsToLabel(newNumber: Int) {
        _viewState.value = HomeViewState.Loaded(
            numberOfCommentsToLabel = newNumber
        )
    }
}
