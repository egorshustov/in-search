package com.egorshustov.vpoiske.core.domain.search

import com.egorshustov.vpoiske.core.common.base.UseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.SearchesRepository
import com.egorshustov.vpoiske.core.model.data.Search
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class GetSearchUseCaseParams(val searchId: Long)

class GetSearchUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : UseCase<GetSearchUseCaseParams, Result<Search>>(ioDispatcher) {

    override suspend fun execute(parameters: GetSearchUseCaseParams): Result<Search> =
        searchesRepository.getSearch(parameters.searchId)
}