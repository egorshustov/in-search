package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.egorshustov.vpoiske.feature.params.FriendsRangeState
import com.egorshustov.vpoiske.feature.params.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FriendsRangeLayout(
    friendsRangeState: FriendsRangeState,
    onNeedToSetFriendsRangeChanged: (Boolean) -> Unit,
    onSelectedFriendsRangeChanged: (minCount: Int, maxCount: Int) -> Unit,
) {
    var friendsRangeSliderPosition by remember {
        mutableStateOf(
            friendsRangeState.selectedFriendsMinCount.toFloat()
                    ..friendsRangeState.selectedFriendsMaxCount.toFloat()
        )
    }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = friendsRangeState.needToSetFriendsRange,
                onCheckedChange = onNeedToSetFriendsRangeChanged
            )
            Text(text = stringResource(R.string.search_params_set_friends_limit))
        }

        if (friendsRangeState.needToSetFriendsRange) {
            Text(
                text = stringResource(
                    R.string.search_params_from_to,
                    friendsRangeSliderPosition.start.roundToInt().toString(),
                    friendsRangeSliderPosition.endInclusive.roundToInt().toString()
                ),
                fontWeight = FontWeight.Bold
            )
            RangeSlider(
                values = friendsRangeSliderPosition,
                onValueChange = { range ->
                    friendsRangeSliderPosition = if (range.start > range.endInclusive) {
                        // somehow RangeSlider allows this situation to occur
                        range.endInclusive..range.start
                    } else {
                        range
                    }
                },
                valueRange = 0f..1000f,
                onValueChangeFinished = {
                    onSelectedFriendsRangeChanged(
                        friendsRangeSliderPosition.start.roundToInt(),
                        friendsRangeSliderPosition.endInclusive.roundToInt(),
                    )
                },
                steps = 1000
            )
        }
    }
}


@Preview
@Composable
internal fun FriendsRangeLayoutPreview() {
    FriendsRangeLayout(
        friendsRangeState = FriendsRangeState(),
        onNeedToSetFriendsRangeChanged = {},
        onSelectedFriendsRangeChanged = { _, _ -> },
    )
}