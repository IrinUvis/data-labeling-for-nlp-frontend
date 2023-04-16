package it.nlp.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.nlp.frontend.domain.token.usecase.ClearTokenUseCase
import it.nlp.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.nlp.frontend.domain.token.usecase.GetTokenFlowUseCase
import it.nlp.frontend.domain.token.usecase.StoreTokenUseCase
import it.nlp.frontend.domain.token.usecase.impl.ProdClearTokenUseCase
import it.nlp.frontend.domain.authentication.usecase.impl.ProdCredentialsLogInOrSignUpUseCase
import it.nlp.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.nlp.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.nlp.frontend.domain.comments.usecase.impl.ProdGetCommentsToLabelUseCase
import it.nlp.frontend.domain.comments.usecase.impl.ProdSaveLabeledCommentsUseCase
import it.nlp.frontend.domain.reminder.usecase.CancelCommentLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.GetCommentLabelingReminderStatusUseCase
import it.nlp.frontend.domain.reminder.usecase.GetReminderTimeFlowUseCase
import it.nlp.frontend.domain.reminder.usecase.ScheduleCommentLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdCancelCommentLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdGetCommentLabelingReminderStatusUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdGetReminderTimeFlowUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdScheduleCommentLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdStoreReminderTimeUseCase
import it.nlp.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.nlp.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.nlp.frontend.domain.theme.usecase.impl.ProdGetThemeFlowUseCase
import it.nlp.frontend.domain.theme.usecase.impl.ProdSavePreferredThemeUseCase
import it.nlp.frontend.domain.token.usecase.impl.ProdGetTokenFlowUseCase
import it.nlp.frontend.domain.token.usecase.impl.ProdStoreTokenUseCase

@Suppress("TooManyFunctions")
@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindCredentialsLogInOrSignUpUseCase(
        credentialsLogInOrSignUpUseCase: ProdCredentialsLogInOrSignUpUseCase,
    ): CredentialsLogInOrSignUpUseCase

    @Binds
    abstract fun bindClearTokenUseCase(
        clearTokenUseCase: ProdClearTokenUseCase,
    ): ClearTokenUseCase

    @Binds
    abstract fun bindGetTokenFlowUseCase(
        getTokenFlowUseCase: ProdGetTokenFlowUseCase,
    ): GetTokenFlowUseCase

    @Binds
    abstract fun bindStoreTokenUseCase(
        storeTokenUseCase: ProdStoreTokenUseCase,
    ): StoreTokenUseCase

    @Binds
    abstract fun bindSavePreferredThemeUseCase(
        savePreferredThemeUseCase: ProdSavePreferredThemeUseCase,
    ): SavePreferredThemeUseCase

    @Binds
    abstract fun getThemeFlowUseCase(
        getThemeFlowUseCase: ProdGetThemeFlowUseCase,
    ): GetThemeFlowUseCase

    @Binds
    abstract fun bindGetCommentsToLabelUseCase(
        getCommentsToLabelUseCase: ProdGetCommentsToLabelUseCase,
    ): GetCommentsToLabelUseCase

    @Binds
    abstract fun bindSaveLabeledCommentsUseCase(
        saveLabeledCommentsUseCase: ProdSaveLabeledCommentsUseCase,
    ): SaveLabeledCommentsUseCase

    @Binds
    abstract fun getCancelCommentLabelingRemindersUseCase(
        cancelCommentLabelingRemindersUseCase: ProdCancelCommentLabelingRemindersUseCase,
    ): CancelCommentLabelingRemindersUseCase

    @Binds
    abstract fun bindGetCommentLabelingReminderStatusUseCase(
        getCommentLabelingReminderStatusUseCase: ProdGetCommentLabelingReminderStatusUseCase,
    ): GetCommentLabelingReminderStatusUseCase

    @Binds
    abstract fun bindScheduleCommentLabelingRemindersUseCase(
        scheduleCommentLabelingRemindersUseCase: ProdScheduleCommentLabelingRemindersUseCase,
    ): ScheduleCommentLabelingRemindersUseCase

    @Binds
    abstract fun bindGetReminderTimeFlowUseCase(
        getReminderTimeFlowUseCase: ProdGetReminderTimeFlowUseCase
    ): GetReminderTimeFlowUseCase

    @Binds
    abstract fun bindStoreReminderTimeUseCase(
        storeReminderTimeUseCase: ProdStoreReminderTimeUseCase
    ): StoreReminderTimeUseCase
}
