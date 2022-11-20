package com.egorshustov.insearch.core.network.model.getcities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCitiesInnerResponse(

    @SerialName("title")
    val count: Int? = null,

    @SerialName("items")
    val cityResponseList: List<CityResponse>? = null
)