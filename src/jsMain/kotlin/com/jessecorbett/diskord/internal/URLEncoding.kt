package com.jessecorbett.diskord.internal

external fun encodeURIComponent(uri: String): String

internal actual fun urlEncode(input: String): String = encodeURIComponent(input)
