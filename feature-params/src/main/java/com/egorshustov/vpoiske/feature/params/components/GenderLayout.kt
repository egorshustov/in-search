package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.model.data.Gender
import com.egorshustov.vpoiske.core.ui.component.AppDropdownMenu
import com.egorshustov.vpoiske.feature.params.GenderState
import com.egorshustov.vpoiske.feature.params.R

@Composable
internal fun GenderLayout(
    genderState: GenderState,
    onGenderItemClick: (gender: Gender) -> Unit
) {
    val genderValues = remember { Gender.values().toList() }

    Column {
        Text(text = stringResource(R.string.params_gender))
        Spacer(modifier = Modifier.height(4.dp))
        AppDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            items = genderValues,
            onItemClick = { onGenderItemClick(it) },
            itemText = { item -> Text(item.getDescription(LocalContext.current)) },
            selectedItemValue = genderState.selectedGender.getDescription(LocalContext.current)
        )
    }
}

@Preview
@Composable
internal fun GenderLayoutPreview() {
    GenderLayout(
        genderState = GenderState(),
        onGenderItemClick = {}
    )
}