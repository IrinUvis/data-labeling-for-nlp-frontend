package it.winter2223.bachelor.ak.frontend.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.frontend.data.comments.repository.CommentRepository
import it.winter2223.bachelor.ak.frontend.data.comments.repository.impl.NetworkCommentRepository
import it.winter2223.bachelor.ak.frontend.data.token.repository.impl.DataStoreTokenRepository
import it.winter2223.bachelor.ak.frontend.data.authentication.repository.impl.DemoLogInRepository
import it.winter2223.bachelor.ak.frontend.data.authentication.repository.LogInRepository
import it.winter2223.bachelor.ak.frontend.data.token.repository.TokenRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCommentRepository(
        commentRepository: NetworkCommentRepository
    ): CommentRepository

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DataStoreTokenRepository
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        logInRepository: DemoLogInRepository
    ): LogInRepository
}
