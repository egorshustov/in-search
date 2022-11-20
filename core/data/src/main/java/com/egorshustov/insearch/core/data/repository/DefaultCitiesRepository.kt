package com.egorshustov.insearch.core.data.repository

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.mapResult
import com.egorshustov.insearch.core.model.data.City
import com.egorshustov.insearch.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.datasource.CitiesNetworkDataSource
import com.egorshustov.insearch.core.network.model.getcities.asExternalModelList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultCitiesRepository @Inject constructor(
    private val citiesNetworkDataSource: CitiesNetworkDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CitiesRepository {

    override fun getCities(
        getCitiesParams: GetCitiesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<City>>> = citiesNetworkDataSource
        .getCities(getCitiesParams, commonParams)
        .mapResult { it.asExternalModelList() }
        .flowOn(ioDispatcher)
}