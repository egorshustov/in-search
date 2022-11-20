package com.egorshustov.insearch.core.domain.country

import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.data.repository.CountriesRepository
import com.egorshustov.insearch.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
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