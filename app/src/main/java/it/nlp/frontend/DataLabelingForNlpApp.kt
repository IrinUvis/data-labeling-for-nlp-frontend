package it.nlp.frontend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import it.nlp.frontend.util.reminder.TextsLabelingNotificationHandler.createNotificationChannel

@HiltAndroidApp
class DataLabelingForNlpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }
}
