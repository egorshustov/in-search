package com.egorshustov.vpoiske.core.common.utils

import java.text.SimpleDateFormat
import java.util.*
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
value class Millis(val count: Long) : Comparable<Millis> {

    override fun compareTo(other: Millis): Int = (count - other.count).toInt()
}
typealias UnixMillis = Millis

fun Millis.toSeconds(): Seconds = Seconds((count / MILLIS_IN_SECOND).toInt())

fun Millis.toDuration(): Duration = count.toDuration(DurationUnit.MILLISECONDS)

val currentTime: UnixMillis
    get() = UnixMillis(System.currentTimeMillis())

private fun initCalendar(unixSeconds: UnixSeconds): Calendar =
    Calendar.getInstance().apply { timeInMillis = unixSeconds.toMillis().count }

private fun initCalendar(unixMillis: UnixMillis): Calendar =
    Calendar.getInstance().apply { timeInMillis = unixMillis.count }

fun UnixSeconds.isCurrentYear(): Boolean {
    val year = initCalendar(this).get(Calendar.YEAR)
    val currentYear = initCalendar(currentTime).get(Calendar.YEAR)
    return year == currentYear
}

fun UnixSeconds.isCurrentDay(): Boolean {
    val dayOfYear = initCalendar(this).get(Calendar.DAY_OF_YEAR)
    val currentDayOfYear = initCalendar(currentTime).get(Calendar.DAY_OF_YEAR)
    return (dayOfYear == currentDayOfYear) && isCurrentYear()
}

fun UnixSeconds.getFormattedDateTimeText(): String {
    val calendar = initCalendar(this)
    val formatPattern = when {
        isCurrentDay() -> "HH:mm"
        isCurrentYear() -> "d MMM"
        else -> "d MMM yyyy"
    }
    return SimpleDateFormat(formatPattern, Locale.getDefault()).format(calendar.time).removeDots()
}
