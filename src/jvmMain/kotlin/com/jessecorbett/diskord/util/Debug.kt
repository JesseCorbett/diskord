package com.jessecorbett.diskord.util

internal val DEBUG_MODE by lazy {
    System.getProperty("com.jessecorbett.diskord.debug")?.toBoolean() ?: false
}
