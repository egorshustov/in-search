package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.ui.component.AppDropdownMenu
import com.egorshustov.vpoiske.feature.params.CountriesState

@Composable
internal fun CountryAndCityLayout(
    countriesState: CountriesState,
    onCountryItemClick: (country: Country) -> Unit,
) {

    AppDropdownMenu(
        items = countriesState.countries,
        onItemClick = { onCountryItemClick(it) },
        itemText = { item -> Text(item.title) },
        selectedItemValue = countriesState.selectedCountry?.title.orEmpty()
    )
}