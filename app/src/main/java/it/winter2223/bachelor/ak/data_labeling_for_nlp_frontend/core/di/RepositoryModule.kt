package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.repository.CommentRepository
import it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.commentlabeling.domain.repository.NetworkCommentRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCommentRepository(
        commentRepository: NetworkCommentRepository
    ): CommentRepository
}
