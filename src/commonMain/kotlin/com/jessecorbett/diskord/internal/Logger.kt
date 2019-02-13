package com.jessecorbett.diskord.internal

expect class Logger constructor(name: String) {
    fun debug(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun error(message: String, error: Throwable? = null)
}
