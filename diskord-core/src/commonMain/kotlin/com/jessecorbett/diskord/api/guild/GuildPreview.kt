package com.jessecorbett.diskord.api.guild

import com.jessecorbett.diskord.api.common.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GuildPreview(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val iconHash: String?,
    @SerialName("splash") val splashHash: String?,
    @SerialName("discovery_splash") val discoverySplashHash: String?,
    @SerialName("emojis") val emojis: List<Emoji>,
    @SerialName("features") val features: List<GuildFeatures>,
    @SerialName("description") val description: String?,
    @SerialName("approximate_member_count") val approximateMemberCount: Int? = null,
    @SerialName("approximate_presence_count") val approximatePresenceCount: Int? = null
)
