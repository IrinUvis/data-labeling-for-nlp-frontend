package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.usecase.CredentialsLogInOrSignUpUseCase
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.usecase.ProdCredentialsLogInOrSignUpUseCase

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindCredentialsLogInUseCase(
        credentialsLoginUseCase: ProdCredentialsLogInOrSignUpUseCase,
    ): CredentialsLogInOrSignUpUseCase
}
