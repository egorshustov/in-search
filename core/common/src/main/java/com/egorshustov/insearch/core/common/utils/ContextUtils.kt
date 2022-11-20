package com.egorshustov.insearch.core.common.utils

import android.content.Context
import android.widget.Toast


enum class MessageLength { SHORT, LONG }

/**
 * Universal way to display a toast message to a user.
 * @param message The text that should be shown to the user
 * @param duration How long to display the message
 */
fun Context.showMessage(message: String, duration: MessageLength = MessageLength.LONG) {
    val toastDuration = if (duration == MessageLength.LONG) {
        Toast.LENGTH_LONG
    } else {
        Toast.LENGTH_SHORT
    }
    Toast.makeText(this, message, toastDuration).show()
}