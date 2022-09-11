package com.egorshustov.vpoiske.core.domain.country

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.CountriesRepository
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class RequestCountriesUseCaseParams(
    val commonParams: VkCommonRequestParams,
    val getCountriesParams: GetCountriesRequestParams = GetCountriesRequestParams()
)

class RequestCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<RequestCountriesUseCaseParams, Unit>(ioDispatcher) {

    override fun execute(parameters: RequestCountriesUseCaseParams): Flow<Result<Unit>> =
        countriesRepository.requestCountries(parameters.getCountriesParams, parameters.commonParams)
}