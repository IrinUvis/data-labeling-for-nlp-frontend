package it.winter2223.bachelor.ak.frontend.domain.theme.usecase.impl

import android.util.Log
import it.winter2223.bachelor.ak.frontend.data.theme.repository.ThemeRepository
import it.winter2223.bachelor.ak.frontend.domain.theme.model.GetThemeFlowResult
import it.winter2223.bachelor.ak.frontend.domain.theme.model.Theme
import it.winter2223.bachelor.ak.frontend.domain.theme.usecase.GetThemeFlowUseCase
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProdGetThemeFlowUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
) : GetThemeFlowUseCase {
    companion object {
        private const val TAG = "ProdGetThemeFlowUC"
    }

    override suspend fun invoke(): GetThemeFlowResult {
        return try {
            GetThemeFlowResult.Success(
                themeFlow = themeRepository.tokenFlow().map {
                    Theme.valueOf(it.name)
                }
            )
        } catch (e: IOException) {
            Log.e(TAG, "An error has occurred while retrieving theme from DataStore", e)
            GetThemeFlowResult.Failure
        }
    }
}
