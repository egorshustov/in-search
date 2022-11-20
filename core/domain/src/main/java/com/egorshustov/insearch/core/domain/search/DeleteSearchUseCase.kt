package com.egorshustov.insearch.core.domain.search

import com.egorshustov.insearch.core.common.base.SafeUseCase
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.data.repository.SearchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DeleteSearchUseCaseParams(val searchId: Long)

class DeleteSearchUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : SafeUseCase<DeleteSearchUseCaseParams, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: DeleteSearchUseCaseParams) =
        searchesRepository.deleteSearch(parameters.searchId)
}