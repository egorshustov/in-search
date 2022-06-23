package com.egorshustov.vpoiske.feature.auth.login_auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.egorshustov.vpoiske.core.common.utils.showMessage
import com.egorshustov.vpoiske.feature.auth.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun LoginAuthScreen(
    state: LoginAuthState,
    onTriggerEvent: (LoginAuthEvent) -> Unit,
    onAuthFinished: () -> Unit,
    modifier: Modifier
) {
    if (state.needToFinishAuth) {
        onAuthFinished()
        onTriggerEvent(LoginAuthEvent.OnNeedToFinishAuthProcessed)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            value = state.typedLoginText,
            onValueChange = { onTriggerEvent(LoginAuthEvent.OnUpdateLogin(it)) },
            label = { Text(text = stringResource(R.string.email_or_phone)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            //textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
        )
        TextField(
            modifier = modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            value = state.typedPasswordText,
            onValueChange = { onTriggerEvent(LoginAuthEvent.OnUpdatePassword(it)) },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onTriggerEvent(LoginAuthEvent.OnStartAuthProcess)
                    keyboardController?.hide()
                }
            ),
            //textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
        )
        Button(
            modifier = modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            onClick = { onTriggerEvent(LoginAuthEvent.OnStartAuthProcess) }
        ) {
            Text(stringResource(R.string.login))
        }
    }

    if (state.isLoading && !state.needToFinishAuth) {
        val context = LocalContext.current
        AuthProcessWebView(
            login = state.typedLoginText,
            password = state.typedPasswordText,
            onAuthDataObtained = { userId, accessToken ->
                onTriggerEvent(LoginAuthEvent.OnAuthDataObtained(userId, accessToken))
            },
            onError = {
                context.run { showMessage(getString(it.errorResId)) }
                onTriggerEvent(LoginAuthEvent.OnAuthError)
            }
        )
    }
}