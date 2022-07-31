package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.ui.component.AppDropdownMenu
import com.egorshustov.vpoiske.feature.params.CitiesState
import com.egorshustov.vpoiske.feature.params.CountriesState
import com.egorshustov.vpoiske.feature.params.R

@Composable
internal fun CountryAndCityLayout(
    countriesState: CountriesState,
    onCountryItemClick: (country: Country?) -> Unit,
    citiesState: CitiesState,
    onCityItemClick: (city: City?) -> Unit
) {
    val countries by remember(countriesState.countries) {
        mutableStateOf(countriesState.countries)
    }

    val cities by remember(citiesState.cities) {
        mutableStateOf(citiesState.cities)
    }

    Column {
        Text(text = stringResource(R.string.search_params_country_and_city))
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            AppDropdownMenu(
                modifier = Modifier.weight(0.5f),
                enabled = countries.isNotEmpty(),
                items = listOf<Country?>(null) + countries,
                onItemClick = { onCountryItemClick(it) },
                itemText = { item -> Text(getCountryText(item)) },
                selectedItemValue = getCountryText(countriesState.selectedCountry)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            AppDropdownMenu(
                modifier = Modifier.weight(0.5f),
                enabled = cities.isNotEmpty(),
                items = listOf<City?>(null) + cities,
                onItemClick = { onCityItemClick(it) },
                itemText = { item -> Text(getCityText(item)) },
                selectedItemValue = getCityText(citiesState.selectedCity)
            )
        }
    }
}

@Composable
private fun getCountryText(country: Country?): String =
    country?.title ?: stringResource(R.string.search_params_country)

@Composable
private fun getCityText(city: City?): String =
    city?.title ?: stringResource(R.string.search_params_city)


@Preview
@Composable
internal fun CountryAndCityLayoutPreview() {
    CountryAndCityLayout(
        countriesState = CountriesState(),
        onCountryItemClick = {},
        citiesState = CitiesState(),
        onCityItemClick = {}
    )
}