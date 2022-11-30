package it.winter2223.bachelor.ak.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.comment.repository.impl.NetworkCommentRepository
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.impl.DataStoreTokenRepository
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.impl.DemoLogInRepository
import it.winter2223.bachelor.ak.frontend.data.remote.authentication.repository.LogInRepository
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.EmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.data.remote.comment.emotionassignment.repository.impl.NetworkEmotionAssignmentRepository
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.winter2223.bachelor.ak.frontend.data.local.reminder.repository.impl.DataStoreReminderTimeRepository
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.data.local.theme.repository.impl.DataStoreThemeRepository
import it.winter2223.bachelor.ak.frontend.data.local.token.repository.TokenRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCommentRepository(
        commentRepository: NetworkCommentRepository
    ): CommentRepository

    @Binds
    abstract fun bindEmotionAssignmentRepository(
        emotionAssignmentRepository: NetworkEmotionAssignmentRepository,
    ): EmotionAssignmentRepository

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DataStoreTokenRepository
    ): TokenRepository

    @Binds
    abstract fun bindThemeRepository(
        themeRepository: DataStoreThemeRepository,
    ): ThemeRepository

    @Binds
    abstract fun bindLoginRepository(
        logInRepository: DemoLogInRepository
    ): LogInRepository

    @Binds
    abstract fun bindReminderTimeRepository(
        reminderTimeRepository: DataStoreReminderTimeRepository
    ): ReminderTimeRepository
}
