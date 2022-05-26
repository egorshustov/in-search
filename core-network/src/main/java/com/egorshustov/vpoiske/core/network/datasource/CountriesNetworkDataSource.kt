package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse
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