package com.egorshustov.vpoiske.core.common.utils

import android.net.Uri

typealias UrlString = String

fun UrlString.prepareUrlAndParseSafely(): Uri? = try {
    Uri.parse(this.replace("#", "?"))
} catch (e: Exception) {
    null
}