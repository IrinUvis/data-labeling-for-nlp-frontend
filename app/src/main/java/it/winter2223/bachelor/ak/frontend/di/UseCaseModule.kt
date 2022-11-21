package it.winter2223.bachelor.ak.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.ClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.GetTokenFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.RefreshTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.StoreTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdClearTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.authentication.usecase.impl.ProdCredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.GetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.SaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl.ProdGetCommentsToLabelUseCase
import it.winter2223.bachelor.ak.frontend.domain.comments.usecase.impl.ProdSaveLabeledCommentsUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdGetTokenFlowUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdRefreshTokenUseCase
import it.winter2223.bachelor.ak.frontend.domain.token.usecase.impl.ProdStoreTokenUseCase

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

    @Binds
    abstract fun bindGetCommentsToLabelUseCase(
        getCommentsToLabelUseCase: ProdGetCommentsToLabelUseCase,
    ): GetCommentsToLabelUseCase

    @Binds
    abstract fun bindSaveLabeledCommentsUseCase(
        saveLabeledCommentsUseCase: ProdSaveLabeledCommentsUseCase,
    ): SaveLabeledCommentsUseCase
}
