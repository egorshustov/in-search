package com.egorshustov.vpoiske.core.data.mappers

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.database.model.CountryEmbedded
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse

internal fun Country?.asEmbedded() = CountryEmbedded(this?.id, this?.title.orEmpty())

internal fun CountryResponse.asEntity() = CountryEntity(
    id = id ?: NO_VALUE,
    title = title.orEmpty()
)

internal fun List<CountryResponse>.asEntityList() = map { it.asEntity() }