package com.egorshustov.vpoiske.feature.params

import com.egorshustov.vpoiske.core.model.data.Country

internal data class ParamsState(
    val isAuthRequired: Boolean = false,
    val countriesState: CountriesState = CountriesState()
)

internal data class CountriesState(
    val countries: List<Country> = emptyList(),
    val selectedCountry: Country? = null
)