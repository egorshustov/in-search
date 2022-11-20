package com.egorshustov.insearch.core.network.model.getcountries

import com.egorshustov.insearch.core.common.utils.NO_VALUE
import com.egorshustov.insearch.core.model.data.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null
)

fun CountryResponse?.asExternalModel(): Country? = this?.let {
    Country(
        id = id ?: NO_VALUE,
        title = title.orEmpty()
    )
}