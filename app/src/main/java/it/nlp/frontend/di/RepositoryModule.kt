package it.nlp.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.nlp.frontend.data.remote.emotion.texts.repository.CommentRepository
import it.nlp.frontend.data.remote.emotion.texts.repository.impl.NetworkCommentRepository
import it.nlp.frontend.data.local.token.repository.impl.DataStoreTokenRepository
import it.nlp.frontend.data.remote.authentication.repository.impl.NetworkAuthenticationRepository
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.nlp.frontend.data.remote.emotion.assignments.repository.CommentEmotionAssignmentRepository
import it.nlp.frontend.data.remote.emotion.assignments.repository.impl.NetworkCommentEmotionAssignmentRepository
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.nlp.frontend.data.local.reminder.repository.impl.DataStoreReminderTimeRepository
import it.nlp.frontend.data.local.theme.repository.ThemeRepository
import it.nlp.frontend.data.local.theme.repository.impl.DataStoreThemeRepository
import it.nlp.frontend.data.local.token.repository.TokenRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCommentRepository(
        commentRepository: NetworkCommentRepository
    ): CommentRepository

    @Binds
    abstract fun bindEmotionAssignmentRepository(
        emotionAssignmentRepository: NetworkCommentEmotionAssignmentRepository,
    ): CommentEmotionAssignmentRepository

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
        logInRepository: NetworkAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    abstract fun bindReminderTimeRepository(
        reminderTimeRepository: DataStoreReminderTimeRepository
    ): ReminderTimeRepository
}
