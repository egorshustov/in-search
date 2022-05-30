package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.datasource.CitiesNetworkDataSource
import com.egorshustov.vpoiske.core.network.model.getcities.asExternalModelList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
        .map {
            when (it) {
                is Result.Success -> Result.Success(it.data.asExternalModelList())
                is Result.Error -> Result.Error(it.exception)
                Result.Loading -> Result.Loading
            }
        }.flowOn(ioDispatcher)
}