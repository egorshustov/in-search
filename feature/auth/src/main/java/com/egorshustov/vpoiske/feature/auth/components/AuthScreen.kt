package com.egorshustov.vpoiske.feature.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.egorshustov.vpoiske.core.ui.component.LoadingStub
import com.egorshustov.vpoiske.feature.auth.AuthEvent
import com.egorshustov.vpoiske.feature.auth.AuthProcessWebView
import com.egorshustov.vpoiske.feature.auth.AuthState
import com.egorshustov.vpoiske.feature.auth.R

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun AuthScreen(
    state: AuthState,
    onTriggerEvent: (AuthEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    state.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.getText(context))
            // Notify the view model that the message has been dismissed
            onTriggerEvent(AuthEvent.OnMessageShown(message.id))
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.auth_title)) },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(contentAlignment = Alignment.Center) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    value = state.typedLoginText,
                    onValueChange = { onTriggerEvent(AuthEvent.OnUpdateLogin(it)) },
                    label = { Text(text = stringResource(R.string.auth_email_or_phone)) },
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
                    onValueChange = { onTriggerEvent(AuthEvent.OnUpdatePassword(it)) },
                    label = { Text(text = stringResource(R.string.auth_password)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onTriggerEvent(AuthEvent.OnStartAuthProcess)
                            keyboardController?.hide()
                        }
                    ),
                    //textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                Button(
                    modifier = modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    onClick = { onTriggerEvent(AuthEvent.OnStartAuthProcess) }
                ) {
                    Text(stringResource(R.string.auth_login))
                }
            }

            if (state.isAuthInProcess && !state.needToFinishAuth) {
                AuthProcessWebView(
                    login = state.typedLoginText,
                    password = state.typedPasswordText,
                    onAuthDataObtained = { userId, accessToken ->
                        onTriggerEvent(AuthEvent.OnAuthDataObtained(userId, accessToken))
                    },
                    onError = {
                        onTriggerEvent(AuthEvent.OnAuthError(it))
                    }
                )
            }
        }
        if (state.isLoading) LoadingStub()
    }
}