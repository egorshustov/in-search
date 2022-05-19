package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastSeenResponse(

    @SerialName("time")
    val timeUnixSeconds: Int?,

    @SerialName("platform")
    val platform: Int?
)