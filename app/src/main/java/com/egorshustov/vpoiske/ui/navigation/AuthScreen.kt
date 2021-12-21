package com.egorshustov.vpoiske.ui.navigation

sealed class AuthScreen(val route: String) {

    object Login: AuthScreen("auth_login")
}
