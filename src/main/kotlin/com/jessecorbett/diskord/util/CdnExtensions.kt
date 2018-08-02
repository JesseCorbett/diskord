package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.Emoji
import com.jessecorbett.diskord.api.Guild
import com.jessecorbett.diskord.api.User

const val discordCdn = "https://cdn.discordapp.com/"

val User.pngAvatarUrl: String?
    get() = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.png" else null

val User.jpgAvatarUrl: String?
    get() = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.jpg" else null

val User.webpAvatarUrl: String?
    get() = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.webp" else null

val User.gifAvatarUrl: String?
    get() = if (avatarHash != null) "$discordCdn/avatars/$id/$avatarHash.gif" else null

val User.pngDefaultAvatarUrl: String
    get() = "$discordCdn/embed/avatars/${discriminator % 5}.png"


val Emoji.pngUrl: String?
    get() = if (!isUnicode) "$discordCdn/emojis/$id.png" else null

val Emoji.gifUrl: String?
    get() = if (!isUnicode) "$discordCdn/emojis/$id.gif" else null


val Guild.pngIconUrl: String?
    get() = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.png" else null

val Guild.jpgIconUrl: String?
    get() = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.jpg" else null

val Guild.webpIconUrl: String?
    get() = if (iconHash != null) "$discordCdn/icons/$id/$iconHash.webp" else null

val Guild.pngSplashUrl: String?
    get() = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.png" else null

val Guild.jpgSplashUrl: String?
    get() = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.jpg" else null

val Guild.webpSplashUrl: String?
    get() = if (splashHash != null) "$discordCdn/splashes/$id/$splashHash.webp" else null
