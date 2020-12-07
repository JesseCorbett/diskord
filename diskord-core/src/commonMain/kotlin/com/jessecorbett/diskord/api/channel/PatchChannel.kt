package com.jessecorbett.diskord.api.channel

import com.jessecorbett.diskord.api.common.ChannelType
import com.jessecorbett.diskord.api.common.Overwrite
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PatchChannelName(@SerialName("name") val name: String)

@Serializable
public data class PatchChannelType(@SerialName("type") val type: ChannelType)

@Serializable
public data class PatchChannelPosition(@SerialName("position") val position: Int?)

@Serializable
public data class PatchChannelTopic(@SerialName("topic") val topic: String?)

@Serializable
public data class PatchChannelNSFW(@SerialName("nsfw") val isNSFW: Boolean)

@Serializable
public data class PatchChannelUserRateLimit(@SerialName("rate_limit_per_user") val rateLimit: Int?)

@Serializable
public data class PatchChannelBitrate(@SerialName("bitrate") val bitrate: Int?)

@Serializable
public data class PatchChannelUserLimit(@SerialName("user_limit") val userLimit: Int?)

@Serializable
public data class PatchChannelOverwrites(@SerialName("permission_overwrites") val overwrites: List<Overwrite>)

@Serializable
public data class PatchChannelParent(@SerialName("parent_id") val parentId: String?)
