package com.egorshustov.auth.api

import com.egorshustov.core.navigation.FeatureApi

interface AuthFeatureApi : com.egorshustov.core.navigation.FeatureApi {

    fun authRoute(): String
}