package com.jessecorbett.diskord.internal

import java.time.Instant
import java.time.format.DateTimeFormatter

internal actual fun epochSecondNow(): Long = Instant.now().epochSecond

internal actual fun epochMillisNow(): Long = Instant.now().toEpochMilli()

internal actual fun parseRfc1123(timestamp: String): Long =
    DateTimeFormatter.RFC_1123_DATE_TIME.parse(timestamp, Instant::from).epochSecond
