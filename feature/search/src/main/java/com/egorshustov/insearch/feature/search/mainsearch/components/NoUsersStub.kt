package com.egorshustov.insearch.feature.search.mainsearch.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.insearch.core.common.R

@Composable
internal fun NoUsersStub(
    onStartNewSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val padding = 8.dp
    Column(
        modifier = modifier
            .padding(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.search_main_no_users))
        Spacer(modifier = Modifier.size(padding))
        Button(
            modifier = Modifier.padding(padding),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = onStartNewSearchClick
        ) {
            Text(
                text = stringResource(R.string.search_main_start_new_search),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
internal fun NoUsersStubPreview() {
    NoUsersStub(onStartNewSearchClick = {})
}