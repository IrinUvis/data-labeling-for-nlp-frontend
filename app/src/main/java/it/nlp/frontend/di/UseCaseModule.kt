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
import it.nlp.frontend.domain.emotiontexts.usecase.GetNumberOfLabeledTextsUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.GetTextsToLabelUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.SaveLabeledTextsUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.impl.ProdGetNumberOfLabeledTextsUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.impl.ProdGetTextsToLabelUseCase
import it.nlp.frontend.domain.emotiontexts.usecase.impl.ProdSaveLabeledTextsUseCase
import it.nlp.frontend.domain.reminder.usecase.CancelTextsLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.GetTextsLabelingReminderStatusUseCase
import it.nlp.frontend.domain.reminder.usecase.GetReminderTimeFlowUseCase
import it.nlp.frontend.domain.reminder.usecase.ScheduleTextsLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.StoreReminderTimeUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdCancelTextsLabelingRemindersUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdGetTextsLabelingReminderStatusUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdGetReminderTimeFlowUseCase
import it.nlp.frontend.domain.reminder.usecase.impl.ProdScheduleTextsLabelingRemindersUseCase
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
    abstract fun bindGetNumberOfLabeledTextsUseCase(
        getNumberOfLabeledTextsUseCase: ProdGetNumberOfLabeledTextsUseCase
    ): GetNumberOfLabeledTextsUseCase

    @Binds
    abstract fun bindGetTextsToLabelUseCase(
        getTextsToLabelUseCase: ProdGetTextsToLabelUseCase,
    ): GetTextsToLabelUseCase

    @Binds
    abstract fun bindSaveLabeledTextsUseCase(
        saveLabeledTextsUseCase: ProdSaveLabeledTextsUseCase,
    ): SaveLabeledTextsUseCase

    @Binds
    abstract fun bindCancelTextsLabelingRemindersUseCase(
        cancelTextsLabelingRemindersUseCase: ProdCancelTextsLabelingRemindersUseCase,
    ): CancelTextsLabelingRemindersUseCase

    @Binds
    abstract fun bindGetTextsLabelingReminderStatusUseCase(
        getTextsLabelingReminderStatusUseCase: ProdGetTextsLabelingReminderStatusUseCase,
    ): GetTextsLabelingReminderStatusUseCase

    @Binds
    abstract fun bindScheduleTextsLabelingRemindersUseCase(
        scheduleTextsLabelingRemindersUseCase: ProdScheduleTextsLabelingRemindersUseCase,
    ): ScheduleTextsLabelingRemindersUseCase

    @Binds
    abstract fun bindGetReminderTimeFlowUseCase(
        getReminderTimeFlowUseCase: ProdGetReminderTimeFlowUseCase
    ): GetReminderTimeFlowUseCase

    @Binds
    abstract fun bindStoreReminderTimeUseCase(
        storeReminderTimeUseCase: ProdStoreReminderTimeUseCase
    ): StoreReminderTimeUseCase
}
