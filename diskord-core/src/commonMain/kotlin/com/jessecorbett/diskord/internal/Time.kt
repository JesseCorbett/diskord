package com.jessecorbett.diskord.internal

import kotlinx.datetime.Clock

internal fun epochSecondNow(): Long = Clock.System.now().epochSeconds
internal fun epochMillisNow(): Long = Clock.System.now().toEpochMilliseconds()
