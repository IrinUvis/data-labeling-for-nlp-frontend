package it.winter2223.bachelor.ak.frontend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import it.winter2223.bachelor.ak.frontend.util.reminder.CommentLabelingNotificationHandler.createNotificationChannel

@HiltAndroidApp
class DataLabelingForNlpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }
}
