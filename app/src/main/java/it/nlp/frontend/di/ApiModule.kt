package it.nlp.frontend.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.nlp.frontend.data.local.token.repository.TokenRepository
import it.nlp.frontend.data.remote.authentication.repository.AuthenticationService
import it.nlp.frontend.data.remote.core.AuthAuthenticator
import it.nlp.frontend.data.remote.core.AuthInterceptor
import it.nlp.frontend.data.remote.core.ApiClient
import it.nlp.frontend.data.remote.emotion.assignments.repository.TextEmotionAssignmentService
import it.nlp.frontend.data.remote.emotion.texts.repository.EmotionTextService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(tokenRepository: TokenRepository) = AuthInterceptor(tokenRepository)

    @Singleton
    @Provides
    fun providesAuthAuthenticator(tokenRepository: TokenRepository) =
        AuthAuthenticator(tokenRepository)

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .authenticator(authAuthenticator)
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesAuthenticationService(retrofit: Retrofit) =
        retrofit.create(AuthenticationService::class.java)

    @Singleton
    @Provides
    fun providesTextEmotionAssignmentService(retrofit: Retrofit) =
        retrofit.create(TextEmotionAssignmentService::class.java)

    @Singleton
    @Provides
    fun providesEmotionTextService(retrofit: Retrofit) =
        retrofit.create(EmotionTextService::class.java)

    @Singleton
    @Provides
    fun providesApiClient() = ApiClient()
}
