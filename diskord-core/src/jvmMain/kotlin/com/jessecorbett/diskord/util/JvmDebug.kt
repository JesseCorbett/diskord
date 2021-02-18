package com.jessecorbett.diskord.util

internal actual val DEBUG_MODE by lazy {
    System.getProperty("com.jessecorbett.diskord.debug")?.toBoolean() ?: false
}
