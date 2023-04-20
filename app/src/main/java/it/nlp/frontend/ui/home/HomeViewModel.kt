package it.nlp.frontend.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.nlp.frontend.domain.emotiontexts.model.GetNumberOfLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetNumberOfLabeledTextsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNumberOfLabeledTextsUseCase: GetNumberOfLabeledTextsUseCase,
) : ViewModel() {
    companion object {
        private const val INITIAL_NUMBER_OF_TEXTS = 10
    }

    private val _viewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(
            HomeViewState.Loading(
                numberOfTextsToLabel = INITIAL_NUMBER_OF_TEXTS,
            )
        )
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        loadNumberOfAssignedTexts()
    }

    private fun loadNumberOfAssignedTexts() {
        viewModelScope.launch {
            val useCaseResult = getNumberOfLabeledTextsUseCase()
            if (useCaseResult is GetNumberOfLabeledTextsResult.Success) {
                _viewState.update {
                    HomeViewState.Loaded(
                        numberOfTextsToLabel = it.numberOfTextsToLabel,
                        assignmentsCount = 21,
                    )
                }
            }
        }
    }

    fun updateNumberOfTextsToLabel(newNumber: Int) {
        _viewState.update {
            when (it) {
                is HomeViewState.Loading -> HomeViewState.Loading(
                    numberOfTextsToLabel = newNumber
                )

                is HomeViewState.Loaded -> HomeViewState.Loaded(
                    numberOfTextsToLabel = newNumber,
                    assignmentsCount = it.assignmentsCount,
                )
            }
        }
    }
}
