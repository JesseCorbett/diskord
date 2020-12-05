package com.jessecorbett.diskord.internal

public external fun encodeURIComponent(uri: String): String

internal actual fun urlEncode(input: String): String = encodeURIComponent(input)
