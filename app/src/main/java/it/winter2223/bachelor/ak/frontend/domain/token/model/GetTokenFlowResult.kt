package it.winter2223.bachelor.ak.frontend.domain.token.model

import kotlinx.coroutines.flow.Flow

sealed class GetTokenFlowResult {
    data class Success(val tokenFlow: Flow<Token?>) : GetTokenFlowResult()

    object Failure : GetTokenFlowResult()
}
