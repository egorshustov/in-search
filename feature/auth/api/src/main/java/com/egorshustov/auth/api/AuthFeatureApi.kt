package com.egorshustov.auth.api

import com.egorshustov.core.feature_api.FeatureApi

interface AuthFeatureApi : FeatureApi {

    fun authGraphRoute(): String
}