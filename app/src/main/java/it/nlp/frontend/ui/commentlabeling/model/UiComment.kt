package it.nlp.frontend.ui.commentlabeling.model

import it.nlp.frontend.ui.core.helpers.UiText

data class UiComment(
    val id: String,
    val text: UiText.StringText,
    val emotion: UiEmotion? = null,
)
