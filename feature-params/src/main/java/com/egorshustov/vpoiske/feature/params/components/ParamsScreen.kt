package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.feature.params.ParamsEvent
import com.egorshustov.vpoiske.feature.params.ParamsState
import com.egorshustov.vpoiske.feature.params.areSelectionParamsDefault
import com.egorshustov.vpoiske.feature.params.isNewSearchCanBeStarted

@Composable
internal fun ParamsScreen(
    state: ParamsState,
    onTriggerEvent: (ParamsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {

        ResetParamsLayout(
            areSelectionParamsDefault = state.areSelectionParamsDefault,
            onResetParamsClick = { onTriggerEvent(ParamsEvent.OnClickResetParamsToDefault) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        CountryAndCityLayout(
            countriesState = state.countriesState,
            onCountryItemClick = { onTriggerEvent(ParamsEvent.OnSelectCountry(it)) },
            citiesState = state.citiesState,
            onCityItemClick = { onTriggerEvent(ParamsEvent.OnSelectCity(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderLayout(
            genderState = state.genderState,
            onGenderItemClick = { onTriggerEvent(ParamsEvent.OnSelectGender(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AgeRangeLayout(
            ageRangeState = state.ageRangeState,
            onAgeFromItemClick = { onTriggerEvent(ParamsEvent.OnSelectAgeFrom(it)) },
            onAgeToItemClick = { onTriggerEvent(ParamsEvent.OnSelectAgeTo(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RelationLayout(
            relationState = state.relationState,
            onRelationItemClick = { onTriggerEvent(ParamsEvent.OnSelectRelation(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExtraOptionsLayout(
            extraOptionsState = state.extraOptionsState,
            onWithPhoneOnlyChanged = { onTriggerEvent(ParamsEvent.OnChangeWithPhoneOnly(it)) },
            onFoundUsersLimitChanged = { onTriggerEvent(ParamsEvent.OnChangeFoundUsersLimit(it)) },
            onDaysIntervalChanged = { onTriggerEvent(ParamsEvent.OnChangeDaysInterval(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FriendsRangeLayout(
            friendsRangeState = state.friendsRangeState,
            onNeedToSetFriendsRangeChanged = {
                onTriggerEvent(ParamsEvent.OnChangeNeedToSetFriendsRange(it))
            },
            onSelectedFriendsRangeChanged = { minCount, maxCount ->
                onTriggerEvent(ParamsEvent.OnChangeSelectedFriendsRange(minCount, maxCount))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FollowersRangeLayout(
            followersRangeState = state.followersRangeState,
            onSelectedFollowersRangeChanged = { minCount, maxCount ->
                onTriggerEvent(ParamsEvent.OnChangeSelectedFollowersRange(minCount, maxCount))
            }
        )

        Button(
            enabled = state.isNewSearchCanBeStarted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = { onTriggerEvent(ParamsEvent.OnClickStartSearch) }
        ) {
            Text(
                text = stringResource(R.string.params_start_search),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
internal fun ParamsScreenPreview() {
    ParamsScreen(
        state = ParamsState(),
        onTriggerEvent = {},
    )
}