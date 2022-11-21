package it.winter2223.bachelor.ak.frontend.domain.token.usecase

import it.winter2223.bachelor.ak.frontend.domain.token.model.ClearTokenResult

interface ClearTokenUseCase {
    suspend operator fun invoke(): ClearTokenResult
}
