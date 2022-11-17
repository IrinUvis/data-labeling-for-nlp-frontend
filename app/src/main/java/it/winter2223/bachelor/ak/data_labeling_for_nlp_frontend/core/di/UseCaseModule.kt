package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.GetTokenFlowUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.RefreshTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.StoreTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl.ProdClearTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl.ProdCredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl.ProdGetTokenFlowUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl.ProdRefreshTokenUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.usecase.impl.ProdStoreTokenUseCase

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
    abstract fun bindRefreshTokenUseCase(
        refreshTokenUseCase: ProdRefreshTokenUseCase,
    ): RefreshTokenUseCase

    @Binds
    abstract fun bindStoreTokenUseCase(
        storeTokenUseCase: ProdStoreTokenUseCase,
    ): StoreTokenUseCase
}
