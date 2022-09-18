package com.egorshustov.vpoiske.core.common.utils

import android.net.Uri

typealias UrlString = String

fun UrlString.prepareUrlAndParseSafely(): Uri? = try {
    Uri.parse(this.replace("#", "?"))
} catch (e: Exception) {
    null
}

fun String.extractDigits(): String = replace("\\D+".toRegex(), "")

fun String.removeDots(): String = replace(".", "")