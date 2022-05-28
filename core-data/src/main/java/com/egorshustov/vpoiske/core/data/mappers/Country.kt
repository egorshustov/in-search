package com.egorshustov.vpoiske.core.data.mappers

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse

internal fun CountryResponse.asEntity() = CountryEntity(
    id = id ?: NO_VALUE,
    title = title.orEmpty()
)

internal fun List<CountryResponse>.asEntityList() = map { it.asEntity() }