package com.egorshustov.vpoiske.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.egorshustov.vpoiske.feature.auth.components.AuthScreen

@Composable
internal fun AuthRoute(
    modifier: Modifier = Modifier,
    onAuthFinished: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onTriggerEvent = viewModel::onTriggerEvent

    AuthScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        onAuthFinished = onAuthFinished,
        modifier = modifier
    )
}