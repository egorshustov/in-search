package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("area")
    val area: String? = null,

    @SerialName("region")
    val region: String? = null
)