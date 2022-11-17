package it.winter2223.bachelor.ak.data_labeling_for_nlp_frontend.login.domain.model

import kotlinx.coroutines.flow.Flow

sealed class GetTokenFlowResult {
    data class Success(val tokenFlow: Flow<Token?>) : GetTokenFlowResult()

    object Failure : GetTokenFlowResult()
}
