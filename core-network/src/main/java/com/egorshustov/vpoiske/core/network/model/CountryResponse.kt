package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(

    @SerialName("id")
    val id: Int?,

    @SerialName("title")
    val title: String?
)