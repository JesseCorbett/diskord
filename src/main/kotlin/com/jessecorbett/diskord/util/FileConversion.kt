package com.jessecorbett.diskord.util

import java.io.File
import java.io.FileInputStream
import java.util.*

fun File.toByteArray() = ByteArray(this.length().toInt()).also { FileInputStream(this).read(it) }

fun File.toBase64() = Base64.getEncoder().encodeToString(this.toByteArray())!!
