package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.ClearTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
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

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }
}
