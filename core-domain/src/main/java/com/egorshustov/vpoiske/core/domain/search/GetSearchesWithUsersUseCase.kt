package com.egorshustov.vpoiske.core.domain.search

import androidx.paging.PagingData
import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.model.asResult
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.SearchesRepository
import com.egorshustov.vpoiske.core.model.data.SearchWithUsers
import com.egorshustov.vpoiske.core.model.data.requestsparams.PagingConfigParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetSearchesWithUsersParams(
    val params: PagingConfigParams
)

class GetSearchesWithUsersUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetSearchesWithUsersParams, PagingData<SearchWithUsers>>(ioDispatcher) {

    override fun execute(parameters: GetSearchesWithUsersParams): Flow<Result<PagingData<SearchWithUsers>>> =
        searchesRepository
            .getSearchesWithUsersStream(parameters.params)
            .asResult()
}