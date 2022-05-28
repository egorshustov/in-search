package com.egorshustov.vpoiske.core.domain.country

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.model.asResult
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.CountriesRepository
import com.egorshustov.vpoiske.core.model.data.Country
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