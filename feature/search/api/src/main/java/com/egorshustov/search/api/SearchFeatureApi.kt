package com.egorshustov.search.api

import com.egorshustov.core.feature_api.FeatureApi

interface SearchFeatureApi : FeatureApi {

    fun searchGraphRoute(): String
}