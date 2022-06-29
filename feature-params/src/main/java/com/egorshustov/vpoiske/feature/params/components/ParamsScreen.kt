package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.feature.params.ParamsEvent
import com.egorshustov.vpoiske.feature.params.ParamsState

@Composable
internal fun ParamsScreen(
    state: ParamsState,
    onTriggerEvent: (ParamsEvent) -> Unit,
    requireAuth: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isAuthRequired) {
        requireAuth()
        onTriggerEvent(ParamsEvent.OnAuthRequested)
    }

    CountryAndCityLayout(
        countriesState = state.countriesState,
        onCountryItemClick = { onTriggerEvent(ParamsEvent.OnSelectCountry(it)) }
    )

    Spacer(modifier = modifier.height(8.dp))

}