package com.egorshustov.insearch.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.egorshustov.insearch.feature.auth.components.AuthScreen

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun AuthRoute(
    modifier: Modifier = Modifier,
    onAuthFinished: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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