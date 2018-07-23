package com.jessecorbett.diskord.util

import java.io.File
import java.io.FileInputStream
import java.util.*

fun File.toByteArray(): ByteArray {
    val bytes = ByteArray(this.length().toInt())
    FileInputStream(this).read(bytes)
    return bytes
}

fun File.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())!!
