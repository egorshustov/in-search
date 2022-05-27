package com.egorshustov.vpoiske.core.model.data.requestsparams

private const val DEFAULT_GET_CITIES_COUNT = 1000 // take as much as possible

data class GetCitiesRequestParams(
    val countryId: Int,
    val needAll: Boolean = false,
    val searchQuery: String = "",
    val count: Int = DEFAULT_GET_CITIES_COUNT
)