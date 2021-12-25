package com.egorshustov.vpoiske.ui.navigation

sealed class AuthScreen(val screenRoute: String) {

    object Login : AuthScreen("auth_login")

    companion object {
        val graphRoute = "auth"
    }
}