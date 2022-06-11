package com.egorshustov.vpoiske.core.data.mappers

import com.egorshustov.vpoiske.core.database.model.CityEmbedded
import com.egorshustov.vpoiske.core.model.data.City

internal fun City?.asEmbedded() =
    CityEmbedded(this?.id, this?.title.orEmpty(), this?.area.orEmpty(), this?.region.orEmpty())