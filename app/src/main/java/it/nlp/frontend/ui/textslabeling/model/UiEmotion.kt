package it.nlp.frontend.ui.textslabeling.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import it.nlp.frontend.R

enum class UiEmotion(
    @StringRes val resourceText: Int,
    @DrawableRes val resourceIcon: Int,
) {
    Anger(R.string.angerEmotion, R.drawable.ic_sentiment_extremely_dissatisfied_24),
    Fear(R.string.fearEmotion, R.drawable.ic_mood_bad_24),
    Joy(R.string.joyEmotion, R.drawable.ic_mood_24),
    Love(R.string.loveEmotion, R.drawable.ic_sentiment_very_satisfied_24),
    Sadness(R.string.sadnessEmotion, R.drawable.ic_sentiment_dissatisfied_24),
    Surprise(R.string.surpriseEmotion, R.drawable.ic_surprised_24),
    Unspecifiable(R.string.unspecifiableEmotion, R.drawable.ic_baseline_question_mark_24)
}
