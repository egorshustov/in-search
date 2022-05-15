package com.egorshustov.vpoiske.core.network.ktor

import io.ktor.client.*

internal interface KtorClientFactory {

    fun build(): HttpClient
}