package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.model.getcities.CityResponse
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