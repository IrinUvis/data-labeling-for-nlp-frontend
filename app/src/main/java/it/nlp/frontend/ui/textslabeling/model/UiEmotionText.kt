package it.nlp.frontend.ui.textslabeling.model

import it.nlp.frontend.ui.core.helpers.UiText

data class UiEmotionText(
    val id: String,
    val text: UiText.StringText,
    val emotion: UiEmotion? = null,
)
