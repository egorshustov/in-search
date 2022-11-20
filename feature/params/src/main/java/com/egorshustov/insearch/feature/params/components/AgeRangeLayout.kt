package com.egorshustov.insearch.feature.params.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.insearch.core.ui.component.AppDropdownMenu
import com.egorshustov.insearch.feature.params.AgeRangeState
import com.egorshustov.insearch.feature.params.R

@Composable
internal fun AgeRangeLayout(
    ageRangeState: AgeRangeState,
    onAgeFromItemClick: (ageFrom: Int) -> Unit,
    onAgeToItemClick: (ageTo: Int?) -> Unit
) {
    val menuItems = remember { ageRangeState.commonAgeRange.toList() }

    Column {
        Text(text = stringResource(R.string.params_age))
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            AppDropdownMenu(
                modifier = Modifier.weight(0.5f),
                items = menuItems,
                onItemClick = { onAgeFromItemClick(it) },
                itemText = { item -> Text(getAgeFromText(item)) },
                selectedItemValue = getAgeFromText(ageRangeState.selectedAgeFrom)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            AppDropdownMenu(
                modifier = Modifier.weight(0.5f),
                items = listOf<Int?>(null) + menuItems,
                onItemClick = { onAgeToItemClick(it) },
                itemText = { item -> Text(getAgeToText(item)) },
                selectedItemValue = getAgeToText(ageRangeState.selectedAgeTo)
            )
        }
    }
}

@Composable
private fun getAgeFromText(ageFrom: Int): String = stringResource(R.string.params_from, ageFrom)

@Composable
private fun getAgeToText(ageTo: Int?): String = if (ageTo == null) {
    stringResource(R.string.params_to_any)
} else {
    stringResource(R.string.params_to, ageTo)
}

@Preview
@Composable
internal fun AgeRangeLayoutPreview() {
    AgeRangeLayout(
        ageRangeState = AgeRangeState(),
        onAgeFromItemClick = {},
        onAgeToItemClick = {}
    )
}