package it.nlp.frontend.domain.theme.usecase.impl

import it.nlp.frontend.data.local.theme.repository.ThemeRepository
import it.nlp.frontend.domain.theme.model.GetThemeFlowResult
import it.nlp.frontend.domain.theme.model.Theme
import it.nlp.frontend.domain.theme.usecase.GetThemeFlowUseCase
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProdGetThemeFlowUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
) : GetThemeFlowUseCase {
    override suspend fun invoke(): GetThemeFlowResult {
        return try {
            GetThemeFlowResult.Success(
                themeFlow = themeRepository.themeFlow().map {
                    Theme.valueOf(it.name)
                }
            )
        } catch (e: IOException) {
            GetThemeFlowResult.Failure(e)
        }
    }
}
