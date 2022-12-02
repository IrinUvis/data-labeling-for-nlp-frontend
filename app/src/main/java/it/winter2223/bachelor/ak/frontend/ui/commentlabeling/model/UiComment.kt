package it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model

import it.winter2223.bachelor.ak.frontend.ui.core.helpers.UiText

data class UiComment(
    val id: String,
    val text: UiText.StringText,
    val emotion: UiEmotion? = null,
)
