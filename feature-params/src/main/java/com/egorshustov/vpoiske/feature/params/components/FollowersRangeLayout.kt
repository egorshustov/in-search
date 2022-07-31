package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.feature.params.FollowersRangeState
import com.egorshustov.vpoiske.feature.params.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FollowersRangeLayout(
    followersRangeState: FollowersRangeState,
    onSelectedFollowersRangeChanged: (minCount: Int, maxCount: Int) -> Unit,
) {
    var followersRangeSliderPosition by remember(
        followersRangeState.selectedFollowersMinCount,
        followersRangeState.selectedFollowersMaxCount
    ) {
        mutableStateOf(
            followersRangeState.selectedFollowersMinCount.toFloat()
                    ..followersRangeState.selectedFollowersMaxCount.toFloat()
        )
    }

    Column {
        Text(text = stringResource(R.string.search_params_set_followers_limit))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.search_params_from_to,
                followersRangeSliderPosition.start.roundToInt().toString(),
                followersRangeSliderPosition.endInclusive.roundToInt().toString()
            ),
            fontWeight = FontWeight.Bold
        )
        RangeSlider(
            value = followersRangeSliderPosition,
            onValueChange = { range ->
                followersRangeSliderPosition = if (range.start > range.endInclusive) {
                    // somehow RangeSlider allows this situation to occur
                    range.endInclusive..range.start
                } else {
                    range
                }
            },
            valueRange = 0f..1000f,
            onValueChangeFinished = {
                onSelectedFollowersRangeChanged(
                    followersRangeSliderPosition.start.roundToInt(),
                    followersRangeSliderPosition.endInclusive.roundToInt(),
                )
            },
            steps = 1000
        )
    }
}


@Preview
@Composable
internal fun FollowersRangeLayoutPreview() {
    FollowersRangeLayout(
        followersRangeState = FollowersRangeState(),
        onSelectedFollowersRangeChanged = { _, _ -> },
    )
}