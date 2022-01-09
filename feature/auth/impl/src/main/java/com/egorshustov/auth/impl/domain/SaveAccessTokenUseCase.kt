package com.egorshustov.auth.impl.domain

import com.egorshustov.core.common.base.UseCase
import com.egorshustov.core.common.prefs.PreferenceStorage
import com.egorshustov.core.common.utils.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

internal data class SaveAccessTokenUseCaseParams(
    val accessToken: String
)

internal class SaveAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SaveAccessTokenUseCaseParams, Unit>(dispatcher) {

    override suspend fun execute(parameters: SaveAccessTokenUseCaseParams) {
        preferenceStorage.saveAccessToken(parameters.accessToken)
    }
}