package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.rest.FileData
import kotlinx.io.core.buildPacket

/**
 * Converts a [ByteArray] to a [FileData] object.
 *
 * @param offset the offset to start reading from
 * @param length the length to read until
 * @param filename the name of the file
 *
 * @return the [FileData] representing this file
 */
fun ByteArray.toFileData(filename: String, offset: Int = 0, length: Int = size - offset): FileData {
    val packet = buildPacket {
        writeFully(this@toFileData, offset, length)
    }

    return FileData(packet, filename)
}
