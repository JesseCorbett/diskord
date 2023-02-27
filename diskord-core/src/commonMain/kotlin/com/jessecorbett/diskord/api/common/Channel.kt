package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
    public val guildId: String?
    public val position: Int
    public val nsfw: Boolean?
    public val permissionOverwrites: List<Overwrite>
}

public interface GuildText : GuildChannel, TextChannel {
    public val topic: String?
    public val rateLimitPerUser: Int?
    public val parentId: String?
}

public interface Thread : NamedChannel, TextChannel {
    public val guildId: String?
    public val rateLimitPerUser: Int?
    public val ownerId: String?
    public val parentId: String?
    public val messageCount: Int
    public val memberCount: Int
    public val metadata: ThreadMetadata?
    public val member: ThreadMember?
}

@Serializable
public sealed class GuildThread : Channel(), Thread {
    abstract override val id: String
    abstract override val name: String
    abstract override val lastMessageId: String?
    abstract override val lastPinTime: String?
    abstract override val guildId: String?
    abstract override val rateLimitPerUser: Int?
    abstract override val ownerId: String?
    abstract override val parentId: String?
    abstract override val messageCount: Int
    abstract override val memberCount: Int
    abstract override val metadata: ThreadMetadata?
    abstract override val member: ThreadMember?
}

public interface DM : TextChannel {
    public val recipients: List<User>
    public val ownerId: String?
}

@Serializable
@SerialName("0")
public data class GuildTextChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("parent_id") override val parentId: String? = null,
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
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("bitrate") val bitrate: Int,
    @SerialName("user_limit") val userLimit: Int,
    @SerialName("parent_id") val parentId: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("rtc_region") val rtcRegion: String? = null,
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
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean? = null
) : Channel(), GuildChannel

@Serializable
@SerialName("5")
public data class GuildNewsChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), GuildText

@Serializable
@SerialName("6")
public data class GuildStoreChannel(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("parent_id") val parentId: String? = null,
) : Channel(), GuildChannel

@Serializable
@SerialName("7")
public data class PartialChannel(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("type") val type: Int,
    @SerialName("permission_overwrites") val permissionOverwrites: List<Overwrite> = emptyList(),
) : Channel(), NamedChannel

@Serializable
@SerialName("10")
public data class GuildNewsThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : GuildThread()

@Serializable
@SerialName("11")
public data class GuildPublicThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : GuildThread()

@Serializable
@SerialName("12")
public data class GuildPrivateThread(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("owner_id") override val ownerId: String?,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("message_count") override val messageCount: Int,
    @SerialName("member_count") override val memberCount: Int,
    @SerialName("thread_metadata") override val metadata: ThreadMetadata? = null,
    @SerialName("member") override val member: ThreadMember? = null,
) : GuildThread()

@Serializable
@SerialName("13")
public data class GuildStageVoice(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("guild_id") override val guildId: String? = null,
    override val position: Int,
    override val nsfw: Boolean? = null,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList()
) : Channel(), GuildChannel

@Serializable
@SerialName("14")
public data class GuildDirectory(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("guild_id") override val guildId: String? = null,
    override val position: Int,
    override val nsfw: Boolean? = null,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList()
) : Channel(), GuildChannel

@Serializable
@SerialName("15")
public data class GuildForum(
    @SerialName("id") override val id: String,
    @SerialName("guild_id") override val guildId: String? = null,
    @SerialName("position") override val position: Int,
    @SerialName("permission_overwrites") override val permissionOverwrites: List<Overwrite> = emptyList(),
    @SerialName("name") override val name: String,
    @SerialName("topic") override val topic: String? = null,
    @SerialName("nsfw") override val nsfw: Boolean? = null,
    @SerialName("last_message_id") override val lastMessageId: String?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int? = null,
    @SerialName("parent_id") override val parentId: String? = null,
    @SerialName("last_pin_timestamp") override val lastPinTime: String? = null
) : Channel(), GuildText

@Serializable(with = ChannelTypeSerializer::class)
public enum class ChannelType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4),
    GUILD_NEWS(5),
    GUILD_STORE(6),
    GUILD_NEWS_THREAD(10),
    GUILD_PUBLIC_THREAD(11),
    GUILD_PRIVATE_THREAD(12),
    GUILD_STAGE_VOICE(13),
    GUILD_DIRECTORY(14),
    GUILD_FORUM(15)
}

public class ChannelTypeSerializer : CodeEnumSerializer<ChannelType>(ChannelType.UNKNOWN, ChannelType.values())

@Serializable(with = VideoQualityModeSerializer::class)
public enum class VideoQualityMode(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    AUTO(1),
    FULL(2)
}

public class VideoQualityModeSerializer : CodeEnumSerializer<VideoQualityMode>(VideoQualityMode.UNKNOWN, VideoQualityMode.values())

@Serializable
public data class Overwrite(
    @SerialName("id") val id: String,
    @SerialName("type") val type: OverwriteType,
    @SerialName("allow") val allowed: Permissions,
    @SerialName("deny") val denied: Permissions
)

@Serializable(with = OverwriteTypeSerializer::class)
public enum class OverwriteType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    ROLE(0),
    MEMBER(1)
}

public class OverwriteTypeSerializer : KSerializer<OverwriteType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(OverwriteType::class.simpleName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): OverwriteType {
        val value = decoder.decodeString()
        return if (value == "role") {
            OverwriteType.ROLE
        } else if (value == "member") {
            OverwriteType.MEMBER
        } else {
            OverwriteType.values().find { it.code.toString() == value }
        } ?: OverwriteType.UNKNOWN
    }

    override fun serialize(encoder: Encoder, value: OverwriteType) {
        encoder.encodeInt(value.code)
    }
}


@Serializable
public data class ThreadMetadata(
    @SerialName("archived") val archived: Boolean,
    @SerialName("auto_archive_duration") val autoArchiveDuration: Int,
    @SerialName("archive_timestamp") val archiveTimestamp: String,
    @SerialName("locked") val locked: Boolean? = null,
)

@Serializable
public data class ThreadMember(
    @SerialName("id") val id: String?,
    @SerialName("user_id") val userId: String?,
    @SerialName("join_timestamp") val joinTimestamp: String?,
    @SerialName("flags") val flags: Int,
)
