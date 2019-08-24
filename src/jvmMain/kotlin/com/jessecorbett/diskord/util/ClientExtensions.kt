package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.rest.CreateMessage
import com.jessecorbett.diskord.api.rest.client.ChannelClient
import java.io.File
import java.nio.file.Path

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param message The text message to send.
 * @param file The file to attach.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendMessage(file: File) =
    sendMessage(file.toFileData())

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param message The text message to send.
 * @param file The file to attach.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendMessage(message: String, file: File) =
    sendMessage(message, file.toFileData())

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param message The text message to send.
 * @param path The file to attach.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendMessage(path: Path) =
    sendMessage(path.toFileData())

/**
 * Calls [ChannelClient.createMessage] for text messages without needing to create a [CreateMessage] object first.
 *
 * @param message The text message to send.
 * @param path The file to attach.
 *
 * @return the created [Message].
 * @throws com.jessecorbett.diskord.api.exception.DiscordException upon client errors.
 */
suspend fun ChannelClient.sendMessage(message: String, path: Path) =
    sendMessage(message, path.toFileData())
