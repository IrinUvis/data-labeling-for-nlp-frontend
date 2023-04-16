package it.nlp.frontend.domain.token.usecase

import it.nlp.frontend.domain.token.model.ClearTokenResult

interface ClearTokenUseCase {
    suspend operator fun invoke(): ClearTokenResult
}
