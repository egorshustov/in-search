package com.egorshustov.vpoiske.core.model.data.requestsparams

private const val DEFAULT_GET_COUNTRIES_COUNT = 1000 // take as much as possible

data class GetCountriesRequestParams(
    val needAll: Boolean = true,
    val count: Int = DEFAULT_GET_COUNTRIES_COUNT
)