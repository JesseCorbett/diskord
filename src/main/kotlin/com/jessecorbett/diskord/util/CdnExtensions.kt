package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.Emoji
import com.jessecorbett.diskord.api.model.Guild
import com.jessecorbett.diskord.api.model.User
import com.jessecorbett.diskord.api.exception.DiscordBadRequestException
import com.jessecorbett.diskord.api.rest.response.PartialGuild

const val discordCdn = "https://cdn.discordapp.com"

private fun sizeFormat(size: Int?): String {
    if (size == null) {
        return ""
    }
    if (size > 0 && (size and (size - 1)) == 0) {
        return "?size=$size"
    } else {
        throw DiscordBadRequestException("Image size must be a power of two")
    }
}

fun User.pngAvatar(size: Int? = null): String?
    = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.png${sizeFormat(size)}" else null

fun User.jpgAvatar(size: Int? = null): String?
    = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.jpg${sizeFormat(size)}" else null

fun User.webpAvatar(size: Int? = null): String?
    = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.webp${sizeFormat(size)}" else null

fun User.gifAvatar(size: Int? = null): String?
    = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.gif${sizeFormat(size)}" else null

fun User.pngDefaultAvatar(size: Int? = null): String
    = "$discordCdn/embed/avatars/${discriminator % 5}.png${sizeFormat(size)}"


fun Emoji.png(size: Int? = null): String?
    = if (!isUnicode) "$discordCdn/emojis/$id.png${sizeFormat(size)}" else null

fun Emoji.gif(size: Int? = null): String?
    = if (!isUnicode) "$discordCdn/emojis/$id.gif${sizeFormat(size)}" else null


fun Guild.pngIcon(size: Int? = null): String?
    = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.png${sizeFormat(size)}" else null

fun Guild.jpgIcon(size: Int? = null): String?
    = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.jpg${sizeFormat(size)}" else null

fun Guild.webpIcon(size: Int? = null): String?
    = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.webp${sizeFormat(size)}" else null

fun Guild.pngSplash(size: Int? = null): String?
    = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.png${sizeFormat(size)}" else null

fun Guild.jpgSplash(size: Int? = null): String?
    = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.jpg${sizeFormat(size)}" else null

fun Guild.webpSplash(size: Int? = null): String?
    = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.webp${sizeFormat(size)}" else null

fun PartialGuild.pngIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.png${sizeFormat(size)}" else null

fun PartialGuild.jpgIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.jpg${sizeFormat(size)}" else null

fun PartialGuild.webpIcon(size: Int? = null): String?
        = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.webp${sizeFormat(size)}" else null
