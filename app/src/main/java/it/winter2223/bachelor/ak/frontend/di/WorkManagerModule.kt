package it.winter2223.bachelor.ak.frontend.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkManagerModule {

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext appContext: Context) = WorkManager.getInstance(appContext)
}
