package com.jessecorbett.diskord.internal

expect fun epochSecondNow(): Long

expect fun epochMillisNow(): Long

expect fun parseRfc1123(timestamp: String): Long
