package com.egorshustov.search.api

import com.egorshustov.core.navigation.FeatureApi

interface SearchFeatureApi : FeatureApi {

    fun searchRoute(moveToSearchParams: Boolean = false): String
}