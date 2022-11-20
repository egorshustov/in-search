package com.egorshustov.insearch.core.data.repository

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.City
import com.egorshustov.insearch.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {

    fun getCities(
        getCitiesParams: GetCitiesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<City>>>
}