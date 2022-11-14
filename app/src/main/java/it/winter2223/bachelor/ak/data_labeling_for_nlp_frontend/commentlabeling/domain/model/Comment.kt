package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.model

data class Comment(
    val text: String,
    val emotion: Emotion? = null,
)
