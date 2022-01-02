package com.egorshustov.auth.impl.login_auth

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginAuthScreen() {
    Text(text = "LoginAuthScreen")
    /*onClick = {
        navController.navigate(SearchScreen.Main.screenRoute) {
            launchSingleTop = true
            popUpTo(AuthScreen.Login.screenRoute) {
                inclusive = true
            }
        }
    }*/
}