package com.egorshustov.insearch.core.data.repository

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.Country
import com.egorshustov.insearch.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {

    fun getCountriesStream(): Flow<List<Country>>

    fun requestCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<Unit>>
}