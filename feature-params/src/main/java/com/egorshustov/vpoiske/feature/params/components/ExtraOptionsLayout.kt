package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.feature.params.ExtraOptionsState
import com.egorshustov.vpoiske.feature.params.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExtraOptionsLayout(
    extraOptionsState: ExtraOptionsState,
    onWithPhoneOnlyChanged: (Boolean) -> Unit,
    onFoundUsersLimitChanged: (Int) -> Unit,
    onDaysIntervalChanged: (Int) -> Unit
) {
    var foundUsersLimitSliderPosition by remember { mutableStateOf(extraOptionsState.foundUsersLimit.toFloat()) }
    var daysIntervalSliderPosition by remember { mutableStateOf(extraOptionsState.daysInterval.toFloat()) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = extraOptionsState.withPhoneOnly,
                onCheckedChange = onWithPhoneOnlyChanged
            )
            Text(text = stringResource(R.string.search_params_with_phone_only))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = stringResource(R.string.search_params_users_to_find))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = foundUsersLimitSliderPosition.roundToInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = foundUsersLimitSliderPosition,
            onValueChange = { foundUsersLimitSliderPosition = it },
            valueRange = 10f..1000f,
            onValueChangeFinished = {
                onFoundUsersLimitChanged(foundUsersLimitSliderPosition.roundToInt())
            },
            steps = 98
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = stringResource(R.string.search_params_were_online_during))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = daysIntervalSliderPosition.roundToInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = daysIntervalSliderPosition,
            onValueChange = { daysIntervalSliderPosition = it },
            valueRange = 1f..30f,
            onValueChangeFinished = {
                onDaysIntervalChanged(daysIntervalSliderPosition.roundToInt())
            },
            steps = 29
        )
    }
}


@Preview
@Composable
internal fun ExtraOptionsLayoutPreview() {
    ExtraOptionsLayout(
        extraOptionsState = ExtraOptionsState(),
        onWithPhoneOnlyChanged = {},
        onFoundUsersLimitChanged = {},
        onDaysIntervalChanged = {}
    )
}