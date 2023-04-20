package it.nlp.frontend.ui.textslabeling

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.nlp.frontend.R
import it.nlp.frontend.domain.emotiontexts.model.EmotionText
import it.nlp.frontend.domain.emotiontexts.model.Emotion
import it.nlp.frontend.domain.emotiontexts.model.GetTextsToLabelResult
import it.nlp.frontend.domain.emotiontexts.model.SaveLabeledTextsResult
import it.nlp.frontend.domain.emotiontexts.usecase.GetTextsToLabelUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.SaveLabeledTextsUseCase
import it.nlp.frontend.domain.token.usecase.ClearTokenUseCase
import it.nlp.frontend.ui.textslabeling.model.UiEmotionText
import it.nlp.frontend.ui.textslabeling.model.UiEmotion
import it.nlp.frontend.ui.core.helpers.UiText
import it.nlp.frontend.ui.navigation.TEXTS_QUANTITY_ARG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextsLabelingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTextsToLabelUseCase: GetTextsToLabelUseCase,
    private val saveLabeledTextsUseCase: SaveLabeledTextsUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<TextsLabelingViewState> = MutableStateFlow(
        value = TextsLabelingViewState.Loading(UiText.ResourceText(R.string.loadingTexts))
    )
    val viewState: StateFlow<TextsLabelingViewState> = _viewState

    private val textsQuantity: Int = checkNotNull(savedStateHandle[TEXTS_QUANTITY_ARG])

    init {
        viewModelScope.launch {
            loadTexts(quantity = textsQuantity)
        }
    }

    fun onEmotionSelected(emotion: UiEmotion) {
        (_viewState.value as? TextsLabelingViewState.Loaded)?.let {
            _viewState.value = TextsLabelingViewState.Loaded.Active(
                texts = it.texts.withChangedEmotionAtIndex(
                    emotion = emotion,
                    index = it.currentTextIndex
                ),
                currentTextIndex = it.currentTextIndex,
            )
        }
    }

    fun goToPreviousText() {
        (_viewState.value as? TextsLabelingViewState.Loaded)?.let {
            if (it.currentTextIndex > 0) {
                _viewState.value = TextsLabelingViewState.Loaded.Active(
                    texts = it.texts,
                    currentTextIndex = it.currentTextIndex - 1
                )
            }
        }
    }

    fun goToNextText() {
        (_viewState.value as? TextsLabelingViewState.Loaded)?.let {
            if (it.currentTextIndex + 1 != it.texts.size) {
                _viewState.value = TextsLabelingViewState.Loaded.Active(
                    texts = it.texts,
                    currentTextIndex = it.currentTextIndex + 1
                )
            } else {
                viewModelScope.launch {
                    postLabeledTexts(it.texts)

                    // Do not load texts if post was unsuccessful
                    if (_viewState.value is TextsLabelingViewState.Loading) {
                        loadTexts(quantity = textsQuantity)
                    }
                }
            }
        }
    }

    fun checkForUnspecifiableEmotionAndGoToNextText() {
        (_viewState.value as? TextsLabelingViewState.Loaded)?.let {
            if (it.currentText.emotion == UiEmotion.Unspecifiable) {
                _viewState.value =
                    TextsLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested(
                        texts = it.texts,
                        currentTextIndex = it.currentTextIndex,
                    )
            } else {
                goToNextText()
            }
        }
    }

    fun closeDialog() {
        (_viewState.value as? TextsLabelingViewState.Loaded.GoToNextWithUnspecifiableRequested)?.let {
            _viewState.value = TextsLabelingViewState.Loaded.Active(
                texts = it.texts,
                currentTextIndex = it.currentTextIndex,
            )
        }
    }

    fun retryLoading() {
        viewModelScope.launch {
            loadTexts(quantity = textsQuantity)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }

    private suspend fun loadTexts(quantity: Int) {
        _viewState.value = TextsLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.loadingTexts)
        )

        val getTextsToLabelResult = getTextsToLabelUseCase(quantity)
        handleGetTextsToLabelResult(getTextsToLabelResult)
    }

    private suspend fun postLabeledTexts(texts: List<UiEmotionText>) {
        _viewState.value = TextsLabelingViewState.Loading(
            text = UiText.ResourceText(R.string.postingTexts)
        )

        val domainTexts = texts.map { it.toDomainEmotionText() }
        val saveLabeledTextsResult = saveLabeledTextsUseCase(domainTexts)
        handleSaveLabeledTextsResult(
            texts = texts,
            result = saveLabeledTextsResult
        )
    }

    private fun handleSaveLabeledTextsResult(
        texts: List<UiEmotionText>,
        result: SaveLabeledTextsResult,
    ) {
        _viewState.value = when (result) {
            is SaveLabeledTextsResult.Success -> _viewState.value
            is SaveLabeledTextsResult.Failure.UnauthorizedUser,
            -> TextsLabelingViewState.AuthError(
                errorMessage = UiText.ResourceText(R.string.logInAndTryAgainErrorMessage)
            )

            is SaveLabeledTextsResult.Failure.ServiceUnavailable ->
                TextsLabelingViewState.Loaded.TextsPostingError(
                    texts = texts,
                    currentTextIndex = texts.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.serviceUnavailableShortErrorMessage)
                )

            is SaveLabeledTextsResult.Failure.Network -> TextsLabelingViewState.Loaded.TextsPostingError(
                texts = texts,
                currentTextIndex = texts.lastIndex,
                errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
            )

            is SaveLabeledTextsResult.Failure.NonLabeledTexts ->
                TextsLabelingViewState.Loaded.TextsPostingError(
                    texts = texts,
                    currentTextIndex = texts.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.textsNotLabeledErrorMessage)
                )

            is SaveLabeledTextsResult.Failure.WrongEmotionParsing ->
                TextsLabelingViewState.Loaded.TextsPostingError(
                    texts = texts,
                    currentTextIndex = texts.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )

            is SaveLabeledTextsResult.Failure.AlreadyAssignedByThisUser ->
                TextsLabelingViewState.Loaded.TextsPostingError(
                    texts = texts,
                    currentTextIndex = texts.lastIndex,
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )

            is SaveLabeledTextsResult.Failure.Unknown -> TextsLabelingViewState.Loaded.TextsPostingError(
                texts = texts,
                currentTextIndex = texts.lastIndex,
                errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
            )
        }
    }

    private fun handleGetTextsToLabelResult(result: GetTextsToLabelResult) {
        _viewState.value = when (result) {
            is GetTextsToLabelResult.Success -> TextsLabelingViewState.Loaded.Active(
                texts = result.emotionTexts.map { it.toUiEmotionText() },
                currentTextIndex = 0,
            )

            is GetTextsToLabelResult.Failure.UnauthorizedUser,
            -> TextsLabelingViewState.AuthError(
                errorMessage = UiText.ResourceText(R.string.logInAndTryAgainErrorMessage)
            )

            is GetTextsToLabelResult.Failure.ServiceUnavailable -> TextsLabelingViewState.TextsLoadingError(
                errorMessage = UiText.ResourceText(R.string.serviceUnavailableLongErrorMessage)
            )

            is GetTextsToLabelResult.Failure.Network -> TextsLabelingViewState.TextsLoadingError(
                errorMessage = UiText.ResourceText(R.string.networkErrorMessage)
            )

            is GetTextsToLabelResult.Failure.NoTexts -> TextsLabelingViewState.TextsLoadingError(
                errorMessage = UiText.ResourceText(R.string.noMoreTextsToLabelError)
            )

            is GetTextsToLabelResult.Failure.TextsNumberOutOfRange ->
                TextsLabelingViewState.TextsLoadingError(
                    errorMessage = UiText.ResourceText(R.string.unexpectedErrorOccurredErrorMessage)
                )

            is GetTextsToLabelResult.Failure.Unknown -> TextsLabelingViewState.TextsLoadingError(
                errorMessage = UiText.ResourceText(R.string.unknownErrorOccurredErrorMessage)
            )
        }
    }
}

fun List<UiEmotionText>.withChangedEmotionAtIndex(
    emotion: UiEmotion,
    index: Int,
): List<UiEmotionText> {
    val newList = this.toMutableList()
    newList[index] = UiEmotionText(
        id = this[index].id,
        text = this[index].text,
        emotion = emotion,
    )
    return newList
}

fun UiEmotionText.toDomainEmotionText() =
    EmotionText(
        id = id,
        text = text.value,
        emotion = emotion?.name?.let { Emotion.valueOf(it) },
    )

fun EmotionText.toUiEmotionText() = UiEmotionText(
    id = id,
    text = UiText.StringText(text),
    emotion = emotion?.name?.let { UiEmotion.valueOf(it) },
)
