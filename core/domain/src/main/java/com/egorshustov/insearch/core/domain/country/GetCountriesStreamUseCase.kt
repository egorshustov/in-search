package com.egorshustov.insearch.core.domain.country

import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.asResult
import com.egorshustov.insearch.core.data.repository.CountriesRepository
import com.egorshustov.insearch.core.model.data.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesStreamUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Country>>(ioDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<Country>>> =
        countriesRepository.getCountriesStream().asResult()
}