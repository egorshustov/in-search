package com.egorshustov.insearch.core.data.mappers

import com.egorshustov.insearch.core.database.model.CityEmbedded
import com.egorshustov.insearch.core.model.data.City

internal fun City?.asEmbedded() =
    CityEmbedded(this?.id, this?.title.orEmpty(), this?.area.orEmpty(), this?.region.orEmpty())