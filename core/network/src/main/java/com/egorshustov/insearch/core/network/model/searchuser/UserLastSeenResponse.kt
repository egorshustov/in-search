package com.egorshustov.insearch.core.network.model.searchuser

import com.egorshustov.insearch.core.common.utils.UnixSeconds
import com.egorshustov.insearch.core.model.data.UserLastSeen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLastSeenResponse(

    @SerialName("time")
    val timeUnixSeconds: Int? = null,

    @SerialName("platform")
    val platformId: Int? = null
)

fun UserLastSeenResponse?.asExternalModel(): UserLastSeen? = this?.let {
    UserLastSeen(
        time = timeUnixSeconds?.let(::UnixSeconds),
        platformId = platformId
    )
}