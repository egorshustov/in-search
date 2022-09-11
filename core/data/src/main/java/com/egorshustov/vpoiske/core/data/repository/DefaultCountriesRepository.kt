package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.mapResult
import com.egorshustov.vpoiske.core.data.mappers.asEntityList
import com.egorshustov.vpoiske.core.database.datasource.CountriesDatabaseDataSource
import com.egorshustov.vpoiske.core.database.model.asExternalModelList
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.datasource.CountriesNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultCountriesRepository @Inject constructor(
    private val countriesNetworkDataSource: CountriesNetworkDataSource,
    private val countriesDatabaseDataSource: CountriesDatabaseDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CountriesRepository {

    override fun getCountriesStream(): Flow<List<Country>> = countriesDatabaseDataSource
        .getCountriesStream()
        .map { it.asExternalModelList() }
        .flowOn(ioDispatcher)

    override fun requestCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<Unit>> = countriesNetworkDataSource
        .getCountries(getCountriesParams, commonParams)
        .mapResult { countriesDatabaseDataSource.upsertCountries(it.asEntityList()) }
        .flowOn(ioDispatcher)
}