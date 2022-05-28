package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {

    fun getCountriesStream(): Flow<List<Country>>

    fun requestCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<Unit>>
}