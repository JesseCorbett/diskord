package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*

@Serializable
public sealed class Channel {
    public abstract val id: String
}

public interface NamedChannel {
    public val name: String
}

public interface TextChannel {
    public val lastMessageId: String?
    public val lastPinTime: String?
}

public interface GuildChannel : NamedChannel {
    public val guildId: String
    public val position: Int
    public val nsfw: Boolean
    public val permissionOverwrites: List<Overwrite>
}

public interface GuildText : GuildChannel, TextChannel {
    public val topic: String?
    public val rateLimitPerUser: Int
    public val parentId: String?
}

public interface DM : TextChannel {
    public val recipients: List<User>
    public val ownerId: String?
}

@Serializable
@SerialName("0")
public data class GuildTextChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int,
    @SerialName("parent_id") override val parentId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), GuildText

@Serializable
@SerialName("1")
public data class DMChannel(
    @SerialName("id") override val id: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("recipients") override val recipients: List<User>,
    @SerialName("owner_id") override val ownerId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), DM

@Serializable
@SerialName("2")
public data class GuildVoiceChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("bitrate") val bitrate: Int,
    @SerialName("user_limit") val userLimit: Int,
    @SerialName("parent_id") val parentId: String?,
    @SerialName("nsfw") override val nsfw: Boolean
) : Channel(), GuildChannel

@Serializable
@SerialName("3")
public data class GroupDMChannel(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("recipients") override val recipients: List<User>,
    @SerialName("icon") val iconHash: String?,
    @SerialName("owner_id") override val ownerId: String,
    @SerialName("application_id") val applicationId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), NamedChannel, DM

@Serializable
@SerialName("4")
public data class GuildCategory(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean
) : Channel(), GuildChannel

@Serializable
@SerialName("5")
public data class GuildNewsChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int,
    @SerialName("parent_id") override val parentId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), GuildText

@Serializable
@SerialName("6")
public data class GuildStoreChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean,
    @SerialName("parent_id") val parentId: String?,
) : Channel(), GuildChannel

@Serializable
public enum class ChannelType {
    @SerialName("0") GUILD_TEXT,
    @SerialName("1") DM,
    @SerialName("2") GUILD_VOICE,
    @SerialName("3") GROUP_DM,
    @SerialName("4") GUILD_CATEGORY,
    @SerialName("5") GUILD_NEWS,
    @SerialName("6") GUILD_STORE
}

@Serializable
public data class Overwrite(
    @SerialName("id") val id: String,
    @SerialName("type") val type: OverwriteType,
    @SerialName("allow") val allowed: Permissions,
    @SerialName("deny") val denied: Permissions
)

@Serializable
public enum class OverwriteType {
    @SerialName("role") ROLE,
    @SerialName("member") MEMBER
}
