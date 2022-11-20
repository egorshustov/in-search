package com.egorshustov.insearch.core.database.model

data class CityEmbedded(
    val id: Int?,
    val title: String,
    val area: String,
    val region: String
)