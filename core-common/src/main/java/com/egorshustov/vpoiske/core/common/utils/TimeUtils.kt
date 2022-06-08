package com.egorshustov.vpoiske.core.common.utils

@JvmInline
value class Seconds(val count: Int)
typealias UnixSeconds = Seconds

fun Seconds.toMillis() = Millis(count.toLong() * MILLIS_IN_SECOND)

@JvmInline
value class Millis(val count: Long)
typealias UnixMillis = Millis

fun Millis.toSeconds() = Seconds((count / MILLIS_IN_SECOND).toInt())