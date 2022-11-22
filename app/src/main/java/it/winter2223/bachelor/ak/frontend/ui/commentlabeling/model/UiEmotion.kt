package it.winter2223.bachelor.ak.frontend.ui.commentlabeling.model

import androidx.annotation.StringRes
import it.winter2223.bachelor.ak.frontend.R

enum class UiEmotion(@StringRes val resourceText: Int) {
    Anger(R.string.angerEmotion),
    Fear(R.string.fearEmotion),
    Joy(R.string.joyEmotion),
    Love(R.string.loveEmotion),
    Sadness(R.string.sadnessEmotion),
    Surprise(R.string.surpriseEmotion),
}
