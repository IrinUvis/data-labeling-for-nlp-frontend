package it.nlp.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.nlp.frontend.data.local.reminder.repository.ReminderTimeRepository
import it.nlp.frontend.data.local.reminder.repository.impl.DataStoreReminderTimeRepository
import it.nlp.frontend.data.local.theme.repository.ThemeRepository
import it.nlp.frontend.data.local.theme.repository.impl.DataStoreThemeRepository
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.data.local.token.repository.impl.DataStoreTokenRepository
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationRepository
import it.nlp.frontend.data.remote.authentication.repository.impl.NetworkAuthenticationRepository
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.emotion.assignments.repository.impl.NetworkTextEmotionAssignmentRepository
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextRepository
import it.nlp.frontend.data.remote.emotion.texts.repository.impl.NetworkEmotionTextRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        authenticationRepository: NetworkAuthenticationRepository
    ): AuthenticationRepository

    @Binds
    abstract fun bindEmotionTextRepository(
        emotionTextRepository: NetworkEmotionTextRepository
    ): EmotionTextRepository

    @Binds
    abstract fun bindTextEmotionAssignmentRepository(
        textEmotionAssignmentRepository: NetworkTextEmotionAssignmentRepository,
    ): TextEmotionAssignmentRepository

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DataStoreTokenRepository
    ): TokenRepository

    @Binds
    abstract fun bindThemeRepository(
        themeRepository: DataStoreThemeRepository,
    ): ThemeRepository

    @Binds
    abstract fun bindReminderTimeRepository(
        reminderTimeRepository: DataStoreReminderTimeRepository
    ): ReminderTimeRepository
}
