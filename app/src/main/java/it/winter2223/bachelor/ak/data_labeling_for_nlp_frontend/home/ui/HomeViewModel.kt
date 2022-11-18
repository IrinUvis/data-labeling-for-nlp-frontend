package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.AppDestination
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.ClearTokenUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState.Loaded(5))
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.value = HomeViewState.Loaded(
                numberOfCommentsToLabel = 5,
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
            navController.navigate(AppDestination.LogIn.route) {
                popUpTo(AppDestination.Home.route) {
                    inclusive = true
                }
            }
        }
    }
}