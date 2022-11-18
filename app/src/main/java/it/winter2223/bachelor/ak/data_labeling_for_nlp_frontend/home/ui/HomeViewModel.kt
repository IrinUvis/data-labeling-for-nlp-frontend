package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.HomeDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.navigation.navigateToLogIn
import kotlinx.coroutines.delay
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
        MutableStateFlow(HomeViewState.Loading)
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            @Suppress("MagicNumber")
            delay(2000)
            _viewState.value = HomeViewState.Loaded(
                numberOfCommentsToLabel = INITIAL_NUMBER_OF_COMMENTS,
            )
        }
    }

    fun updateNumberOfCommentsToLabel(newNumber: Int) {
        _viewState.value = HomeViewState.Loaded(
            numberOfCommentsToLabel = newNumber
        )
    }

    fun logOut(navController: NavController) {
        viewModelScope.launch {
            clearTokenUseCase()
            navController.navigateToLogIn {
                popUpTo(HomeDestination.route) {
                    inclusive = true
                }
            }
        }
    }
}
