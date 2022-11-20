package com.egorshustov.insearch.core.network.datasource

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.model.getcountries.CountryResponse
import kotlinx.coroutines.flow.Flow

interface CountriesNetworkDataSource {

    /**
     * [database.getCountries VK API](https://dev.vk.com/method/database.getCountries)
     */
    fun getCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<CountryResponse>>>
}