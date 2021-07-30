package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.channel.FileData
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully

/**
 * Converts a [ByteArray] to a [FileData] object.
 *
 * @param offset the offset to start reading from
 * @param length the length to read until
 * @param filename the name of the file
 * @param contentType the content type of the file
 *
 * @return the [FileData] representing this file
 */
public fun ByteArray.toFileData(
    filename: String,
    contentType: String?,
    offset: Int = 0,
    length: Int = size - offset
): FileData {
    val packet = buildPacket {
        writeFully(this@toFileData, offset, length)
    }

    return FileData(packet, filename, contentType)
}

// TODO: This should be removed and merged into the other toFileData function on the next breaking pass.
@Deprecated(
    "Use toFileData(String, String?, Int, Int) instead.",
    ReplaceWith("toFileData(filename, null, offset, length)")
)
public fun ByteArray.toFileData(
    filename: String,
    offset: Int = 0,
    length: Int = size - offset
): FileData = toFileData(filename, null, offset = offset, length = length)
