package com.egorshustov.auth.impl.login_auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.egorshustov.core.common.utils.showMessage

@Composable
internal fun LoginAuthScreen() {
    /*onClick = {
        navController.navigate(SearchScreen.Main.screenRoute) {
            launchSingleTop = true
            popUpTo(AuthScreen.Login.screenRoute) {
                inclusive = true
            }
        }
    }*/
    val context = LocalContext.current
    AuthProcessWebView(
        login = "some_email@mail.ru",
        password = "some_password",
        onAuthDataObtained = { userId, accessToken ->
        },
        onError = {
            context.run { showMessage(getString(it.errorResId)) }
        }
    )
}