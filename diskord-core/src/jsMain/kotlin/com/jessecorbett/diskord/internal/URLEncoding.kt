package com.jessecorbett.diskord.internal

internal external fun encodeURIComponent(uri: String): String

internal actual fun urlEncode(input: String): String = encodeURIComponent(input)
