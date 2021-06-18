package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.channel.FileData
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import java.io.File
import java.io.FileInputStream
import java.nio.file.Path
import java.util.*


/**
 * Converts a [File] to a [ByteArray].
 *
 * @return a [ByteArray] version of the file.
 */
public fun File.toByteArray(): ByteArray {
    return ByteArray(this.length().toInt()).also { FileInputStream(this).read(it) }
}

/**
 * Converts a [File] to a base64 encoded [String].
 *
 * @return a base64 encoded version of the file.
 */
public fun File.toBase64(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray())!!
}

/**
 * Converts a [File] to a [FileData] object.
 *
 * @return the [FileData] representing this file
 */
public fun File.toFileData(): FileData {
    val packet = buildPacket {
        forEachBlock { buffer, bytesRead ->
            writeFully(buffer, length = bytesRead)
        }
    }

    return FileData(packet, name)
}

/**
 * Converts a [Path] to a [FileData] object.
 *
 * @return the [FileData] representing this file
 */
public fun Path.toFileData(): FileData {
    return toFile().toFileData()
}
