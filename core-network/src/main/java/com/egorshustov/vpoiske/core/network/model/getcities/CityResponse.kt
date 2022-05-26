package com.egorshustov.vpoiske.core.network.model.getcities

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.model.data.City
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

fun CityResponse?.asExternalModel(): City? = this?.let {
    City(
        id = id ?: NO_VALUE,
        title = title.orEmpty(),
        area = area.orEmpty(),
        region = region.orEmpty()
    )
}