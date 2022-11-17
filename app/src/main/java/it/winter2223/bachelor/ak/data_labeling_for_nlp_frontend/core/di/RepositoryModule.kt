package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.repository.CommentRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.data.repository.NetworkCommentRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.DataStoreTokenRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.DemoLogInRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.LogInRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.data.repository.TokenRepository

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
