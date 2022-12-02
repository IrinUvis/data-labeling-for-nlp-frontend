package it.winter2223.bachelor.ak.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.impl.ProdCredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl.ProdGetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl.ProdSaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.CancelCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.GetCommentLabelingReminderStatusUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.GetReminderTimeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.ScheduleCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdCancelCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdGetCommentLabelingReminderStatusUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdGetReminderTimeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdScheduleCommentLabelingRemindersUseCase
import it.winter2223.bachelor.ak.frontend.domain.reminder.usecase.impl.ProdStoreReminderTimeUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.SavePreferredThemeUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl.ProdGetThemeFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl.ProdSavePreferredThemeUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdGetTokenFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdStoreTokenUseCase

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
