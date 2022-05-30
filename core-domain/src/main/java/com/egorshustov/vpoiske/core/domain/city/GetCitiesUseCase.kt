package com.egorshustov.vpoiske.core.domain.city

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.CitiesRepository
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetCitiesUseCaseParams(
    val getCitiesParams: GetCitiesRequestParams,
    val commonParams: VkCommonRequestParams
)

class GetCitiesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetCitiesUseCaseParams, List<City>>(ioDispatcher) {

    override fun execute(parameters: GetCitiesUseCaseParams): Flow<Result<List<City>>> =
        citiesRepository.getCities(parameters.getCitiesParams, parameters.commonParams)
}