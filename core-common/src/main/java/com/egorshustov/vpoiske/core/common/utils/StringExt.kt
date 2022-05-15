package com.egorshustov.core.common.utils

import android.net.Uri

fun String.prepareUrlAndParseSafely(): Uri? = try {
    Uri.parse(this.replace("#", "?"))
} catch (e: Exception) {
    null
}