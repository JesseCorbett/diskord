package com.jessecorbett.diskord.util

import kotlinx.datetime.Instant

/**
 * Creates a Discord compatible timestamp, which shows dates and times adjusted for each user's timezone.
 *
 * @param format the format of timestamp to show end users.
 * @return a string for embedding in a message which shows datetimes formatted relative to each user's timezone.
 */
public fun Instant.toTimestamp(format: TimestampFormat = TimestampFormat.RELATIVE): String {
    return timestamp(this, format)
}

/**
 * Creates a Discord compatible timestamp, which shows dates and times adjusted for each user's timezone.
 */
public fun timestamp(time: Instant, format: TimestampFormat): String {
    return "<t:${time.epochSeconds}:${format.key}>"
}

public enum class TimestampFormat(public val key: String) {
    RELATIVE("R"),
    LONG_DATE("D"),
    SHORT_DATE("d"),
    LONG_TIME("T"),
    SHORT_TIME("t"),
    LONG_DATE_TIME("F"),
    SHORT_DATE_TIME("f")
}
