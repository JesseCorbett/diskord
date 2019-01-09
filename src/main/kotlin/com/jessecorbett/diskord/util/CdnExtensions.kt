package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.Emoji
import com.jessecorbett.diskord.api.model.Guild
import com.jessecorbett.diskord.api.model.User
import com.jessecorbett.diskord.api.exception.DiscordBadRequestException
import com.jessecorbett.diskord.api.rest.response.PartialGuild

/**
 * The host of the discord content delivery network.
 */
const val discordCdn = "https://cdn.discordapp.com"

private fun sizeFormat(size: Int?): String {
    if (size == null) {
        return ""
    }

    // Size must be a positive power of two
    if (size <= 0 || (size and (size - 1)) != 0) {
        throw DiscordBadRequestException("Image size must be a power of two")
    }

    return "?size=$size"
}

/**
 * Gets the url of the user's avatar in PNG format.
 *
 * This is recommended as it can fall back on the default avatar if the user does not have a custom avatar.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted avatar or the url of the png formatted default avatar.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun User.pngAvatar(size: Int? = null): String
        = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.png${sizeFormat(size)}" else pngDefaultAvatar(size)

/**
 * Gets the url of the user's avatar in JPG format.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the jpg formatted avatar or null if there is no custom avatar.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun User.jpgAvatar(size: Int? = null): String?
        = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.jpg${sizeFormat(size)}" else null

/**
 * Gets the url of the user's avatar in WEBP format.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the webp formatted avatar or null if there is no custom avatar.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun User.webpAvatar(size: Int? = null): String?
        = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.webp${sizeFormat(size)}" else null


/**
 * Gets the url of the user's avatar in GIF format.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the gif formatted avatar or null if there is no custom avatar.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun User.gifAvatar(size: Int? = null): String?
        = if (avatarHash != null) "$discordCdn/avatars/$id/a_$avatarHash.gif${sizeFormat(size)}" else null


/**
 * Gets the url of the user's default avatar in PNG format.
 *
 * The default avatar may not be used if the user has uploaded a custom avatar.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted avatar.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun User.pngDefaultAvatar(size: Int? = null): String
        = "$discordCdn/embed/avatars/${discriminator % 5}.png${sizeFormat(size)}"


/**
 * Gets the url of the emoji in PNG format, null if the emoji is a unicode character.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted emoji or null if it is unicode.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Emoji.png(size: Int? = null): String?
        = if (!isUnicode) "$discordCdn/emojis/$id.png${sizeFormat(size)}" else null

/**
 * Gets the url of the emoji in GIF format, null if the emoji is a unicode character.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the gif formatted emoji or null if it is unicode.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Emoji.gif(size: Int? = null): String?
        = if (!isUnicode) "$discordCdn/emojis/$id.gif${sizeFormat(size)}" else null


/**
 * Gets the url of the guild icon in PNG format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.pngIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.png${sizeFormat(size)}" else null

/**
 * Gets the url of the guild icon in JPG format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the jpg formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.jpgIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.jpg${sizeFormat(size)}" else null

/**
 * Gets the url of the guild icon in WEBP format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the webp formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.webpIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.webp${sizeFormat(size)}" else null

/**
 * Gets the url of the guild splash in PNG format. Null if there is no splash.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted splash or null if there is no splash.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.pngSplash(size: Int? = null): String?
        = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.png${sizeFormat(size)}" else null

/**
 * Gets the url of the guild splash in JPG format. Null if there is no splash.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the jpg formatted splash or null if there is no splash.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.jpgSplash(size: Int? = null): String?
        = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.jpg${sizeFormat(size)}" else null

/**
 * Gets the url of the guild splash in WEBP format. Null if there is no splash.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the webp formatted splash or null if there is no splash.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun Guild.webpSplash(size: Int? = null): String?
        = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.webp${sizeFormat(size)}" else null

/**
 * Gets the url of the guild icon in PNG format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun PartialGuild.pngIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.png${sizeFormat(size)}" else null

/**
 * Gets the url of the guild icon in PNG format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun PartialGuild.jpgIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.jpg${sizeFormat(size)}" else null

/**
 * Gets the url of the guild icon in PNG format. Null if there is no custom icon.
 *
 * @param size the size of the image. Must be a power of two or null.
 * @return the url of the png formatted emoji or null if there is no icon.
 * @throws DiscordBadRequestException when the size is not null or a power of two.
 */
fun PartialGuild.webpIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.webp${sizeFormat(size)}" else null
