package com.egorshustov.vpoiske.core.network.ktor

import io.ktor.client.statement.*

internal val HttpResponse.isSuccessful: Boolean
    get() = status.value in 200..299