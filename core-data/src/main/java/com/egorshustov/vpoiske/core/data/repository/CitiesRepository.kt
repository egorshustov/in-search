package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {

    fun getCities(
        getCitiesParams: GetCitiesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<City>>>
}