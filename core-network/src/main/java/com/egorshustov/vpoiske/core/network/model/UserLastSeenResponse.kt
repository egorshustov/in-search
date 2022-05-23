package com.egorshustov.vpoiske.core.network.model

import com.egorshustov.vpoiske.core.model.data.UserLastSeen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLastSeenResponse(

    @SerialName("time")
    val timeUnixSeconds: Int? = null,

    @SerialName("platform")
    val platform: Int? = null
)

fun UserLastSeenResponse?.asExternalModel() = this?.let {
    UserLastSeen(
        timeUnixSeconds = timeUnixSeconds,
        platform = platform
    )
}