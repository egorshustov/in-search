package com.egorshustov.vpoiske.core.common.utils

@JvmInline
value class Seconds(val count: Int) : Comparable<Seconds> {

    operator fun minus(other: Seconds): Seconds = Seconds(count - other.count)

    override fun compareTo(other: Seconds): Int = count - other.count

    companion object {
        val Zero = Seconds(0)
    }
}
typealias UnixSeconds = Seconds

fun Seconds.toMillis() = Millis(count.toLong() * MILLIS_IN_SECOND)

@JvmInline
value class Millis(val count: Long)
typealias UnixMillis = Millis

fun Millis.toSeconds() = Seconds((count / MILLIS_IN_SECOND).toInt())

val currentTime: UnixMillis
    get() = UnixMillis(System.currentTimeMillis())