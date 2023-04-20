package it.nlp.frontend.ui.textslabeling

import it.nlp.frontend.ui.textslabeling.model.UiEmotionText
import it.nlp.frontend.ui.core.helpers.UiText

sealed class TextsLabelingViewState {

    val type: TextsLabelingScreenType
        get() {
            return when (this) {
                is Loading -> TextsLabelingScreenType.Loading
                is AuthError -> TextsLabelingScreenType.AuthError
                is TextsLoadingError -> TextsLabelingScreenType.TextsLoadingError
                is Loaded -> TextsLabelingScreenType.Loaded
            }
        }

    data class Loading(
        val text: UiText,
    ) : TextsLabelingViewState()

    data class TextsLoadingError(
        val errorMessage: UiText,
    ) : TextsLabelingViewState()

    data class AuthError(
        val errorMessage: UiText,
    ) : TextsLabelingViewState()

    sealed class Loaded(
        open val texts: List<UiEmotionText>,
        open val currentTextIndex: Int,
    ) : TextsLabelingViewState() {
        val currentText get() = texts[currentTextIndex]
        val progress get() = currentTextIndex.toFloat() / texts.size

        data class TextsPostingError(
            override val texts: List<UiEmotionText>,
            override val currentTextIndex: Int,
            val errorMessage: UiText,
        ) : Loaded(
            texts = texts,
            currentTextIndex = currentTextIndex,
        )

        data class GoToNextWithUnspecifiableRequested(
            override val texts: List<UiEmotionText>,
            override val currentTextIndex: Int,
        ) : Loaded(
            texts = texts,
            currentTextIndex = currentTextIndex,
        )

        data class Active(
            override val texts: List<UiEmotionText>,
            override val currentTextIndex: Int,
        ) : Loaded(
            texts = texts,
            currentTextIndex = currentTextIndex,
        )
    }
}

enum class TextsLabelingScreenType {
    Loading,
    AuthError,
    TextsLoadingError,
    Loaded,
}
