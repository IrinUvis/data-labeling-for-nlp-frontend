package it.nlp.frontend.domain.token.model

import kotlinx.coroutines.flow.Flow

sealed class GetTokenFlowResult {
    data class Success(val tokenFlow: Flow<Token?>) : GetTokenFlowResult()

    data class Failure(val e: Exception) : GetTokenFlowResult()
}
