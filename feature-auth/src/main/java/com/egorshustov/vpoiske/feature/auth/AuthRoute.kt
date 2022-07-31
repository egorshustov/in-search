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

    if (state.needToFinishAuth) {
        onAuthFinished()
        onTriggerEvent(AuthEvent.OnNeedToFinishAuthProcessed)
    }

    AuthScreen(
        state = state,
        onTriggerEvent = onTriggerEvent,
        modifier = modifier
    )
}