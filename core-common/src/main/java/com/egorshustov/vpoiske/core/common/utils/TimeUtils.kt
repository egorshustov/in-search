package com.egorshustov.vpoiske.core.common.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@JvmInline
value class Seconds(val count: Int) : Comparable<Seconds> {

    operator fun minus(other: Seconds): Seconds = Seconds(count - other.count)

    override fun compareTo(other: Seconds): Int = count - other.count

    companion object {
        val Zero = Seconds(0)
    }
}
typealias UnixSeconds = Seconds

fun Seconds.toMillis(): Millis = Millis(count.toLong() * MILLIS_IN_SECOND)

fun Seconds.toDuration(): Duration = count.toDuration(DurationUnit.SECONDS)

@JvmInline
value class Millis(val count: Long)
typealias UnixMillis = Millis

fun Millis.toSeconds(): Seconds = Seconds((count / MILLIS_IN_SECOND).toInt())

fun Millis.toDuration(): Duration = count.toDuration(DurationUnit.MILLISECONDS)

val currentTime: UnixMillis
    get() = UnixMillis(System.currentTimeMillis())