package com.egorshustov.insearch.core.network.model.getcountries

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCountriesInnerResponse(

    @SerialName("count")
    val count: Int? = null,

    @SerialName("items")
    val countryResponseList: List<CountryResponse>? = null
)