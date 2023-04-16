package it.nlp.frontend.domain.theme.model

import kotlinx.coroutines.flow.Flow

sealed class GetThemeFlowResult {
    data class Success(val themeFlow: Flow<Theme>) : GetThemeFlowResult()

    data class Failure(val e: Exception) : GetThemeFlowResult()
}
