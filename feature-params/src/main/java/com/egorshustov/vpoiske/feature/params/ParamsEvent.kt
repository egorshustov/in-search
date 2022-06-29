package com.egorshustov.vpoiske.feature.params

import com.egorshustov.vpoiske.core.model.data.Country

internal sealed interface ParamsEvent {

    object OnAuthRequested : ParamsEvent

    object RequestCountries : ParamsEvent

    data class OnSelectCountry(val country: Country) : ParamsEvent
}