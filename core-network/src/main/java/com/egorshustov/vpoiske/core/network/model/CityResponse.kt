package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(

    @SerialName("id")
    val id: Int?,

    @SerialName("title")
    val title: String?,

    @SerialName("area")
    val area: String?,

    @SerialName("region")
    val region: String?
)