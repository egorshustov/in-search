package com.egorshustov.insearch.core.network.datasource

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.model.getcities.CityResponse
import kotlinx.coroutines.flow.Flow

interface CitiesNetworkDataSource {

    /**
     * [database.getCities VK API](https://dev.vk.com/method/database.getCities)
     */
    fun getCities(
        getCitiesParams: GetCitiesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<CityResponse>>>
}