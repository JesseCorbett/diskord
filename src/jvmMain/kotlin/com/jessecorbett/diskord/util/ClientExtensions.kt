package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.channel.CreateMessage
import com.jessecorbett.diskord.api.channel.ChannelClient
import java.io.File
import java.nio.file.Path

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param file The file to attach.
 * @param comment The comment to send with the file.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendFile(file: File, comment: String = "") =
    sendFile(file.toFileData(), comment)

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param path The file to attach.
 * @param comment The comment to send with the file.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exceptions.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendFile(path: Path, comment: String = "") =
    sendFile(path.toFileData(), comment)
