package com.jessecorbett.diskord.api.common

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public enum class Permission(internal val mask: Long) {
    /**
     * Allows creation of instant invites.
     *
     * Channel Type(s): Text, Voice
     */
    CREATE_INSTANT_INVITE(0x00000001),

    /**
     * Allows kicking members.
     */
    KICK_MEMBERS(0x00000002),

    /**
     * Allows banning members.
     */
    BAN_MEMBERS(0x00000004),

    /**
     * Allows all permissions and bypasses channel permission overwrites.
     */
    ADMINISTRATOR(0x00000008),

    /**
     * Allows management and editing of channels.
     *
     * Channel Type(s): Text, Voice
     */
    MANAGE_CHANNELS(0x00000010),

    /**
     * Allows management and editing of the guild.
     */
    MANAGE_GUILD(0x00000020),

    /**
     * Allows for the addition of reactions to messages.
     *
     * Channel Type(s): Text
     */
    ADD_REACTIONS(0x00000040),

    /**
     * Allows for viewing of audit logs.
     */
    VIEW_AUDIT_LOG(0x00000080),

    /**
     * Allows for using priority speaker in a voice channel.
     *
     * Channel Type(s): Voice
     */
    PRIORITY_SPEAKER(0x00000100),

    /**
     * Allows the user to go live.
     *
     * Channel Type(s): Voice
     */
    STREAM(0x00000200),

    /**
     * Allows guild members to view a channel, which includes reading messages in text channels.
     *
     * Channel Type(s): Text, Voice
     */
    VIEW_CHANNEL(0x00000400),

    /**
     * Allows for sending messages in a channel.
     *
     * Channel Type(s): Text
     */
    SEND_MESSAGES(0x00000800),

    /**
     * Allows for sending of `/tts` messages.
     *
     * Channel Type(s): Text
     */
    SEND_TTS_MESSAGES(0x00001000),

    /**
     * Allows for deletion of other users messages.
     *
     * Channel Type(s): Text
     */
    MANAGE_MESSAGES(0x00002000),

    /**
     * Links sent by users with this permission will be auto-embedded.
     *
     * Channel Type(s): Text
     */
    EMBED_LINKS(0x00004000),

    /**
     * Allows for uploading images and files.
     *
     * Channel Type(s): Text
     */
    ATTACH_FILES(0x00008000),

    /**
     * Allows for reading of message history.
     *
     * Channel Type(s): Text
     */
    READ_MESSAGE_HISTORY(0x00010000),

    /**
     * Allows for using the `@everyone` tag to notify all users in a channel, and the `@here` tag to notify all
     * online users in a channel.
     *
     * Channel Type(s): Text
     */
    MENTION_EVERYONE(0x00020000),

    /**
     * Allows the usage of custom emojis from other servers.
     *
     * Channel Type(s): Text
     */
    USE_EXTERNAL_EMOJIS(0x00040000),

    /**
     * Allows for viewing guild insights.
     */
    VIEW_GUILD_INSIGHTS(0x00080000),

    /**
     * Allows for joining of a voice channel.
     *
     * Channel Type(s): Voice
     */
    CONNECT(0x00100000),

    /**
     * Allows for speaking in a voice channel.
     *
     * Channel Type(s): Voice
     */
    SPEAK(0x00200000),

    /**
     * Allows for muting members in a voice channel.
     *
     * Channel Type(s): Voice
     */
    MUTE_MEMBERS(0x00400000),

    /**
     * Allows for deafening of members in a voice channel.
     *
     * Channel Type(s): Voice
     */
    DEAFEN_MEMBERS(0x00800000),

    /**
     * Allows for moving of members between voice channels.
     *
     * Channel Type(s): Voice
     */
    MOVE_MEMBERS(0x01000000),

    /**
     * Allows for using voice-activity-detection in a voice channel.
     *
     * Channel Type(s): Voice
     */
    USE_VAD(0x02000000),

    /**
     * Allows for modification of own nickname.
     */
    CHANGE_NICKNAME(0x04000000),

    /**
     * Allows for modification of other users nicknames.
     */
    MANAGE_NICKNAMES(0x08000000),

    /**
     * Allows management and editing of roles.
     *
     * Channel Type(s): Text, Voice
     */
    MANAGE_ROLES(0x10000000),

    /**
     * Allows management and editing of webhooks.
     *
     * Channel Type(s): Text, Voice
     */
    MANAGE_WEBHOOKS(0x20000000),

    /**
     * Allows management and editing of emojis.
     */
    @Deprecated("Use MANAGE_EMOJIS_AND_STICKERS instead.", ReplaceWith("MANAGE_EMOJIS_AND_STICKERS"))
    MANAGE_EMOJIS(0x40000000),

    /**
     * Allows management and editing of emojis.
     */
    MANAGE_EMOJIS_AND_STICKERS(0x40000000),

    /**
     * Allows for deleting and archiving threads, and viewing all private threads.
     *
     * Channel Type(s): Text
     */
    MANAGE_THREADS(0x0400000000),

    /**
     * Allows for creating and participating in threads.
     *
     * Channel Type(s): Text
     */
    USE_PUBLIC_THREADS(0x0800000000),

    /**
     * Allows for creating and participating in private threads.
     *
     * Channel Type(s): Text
     */
    USE_PRIVATE_THREADS(0x1000000000),

    /**
     * Allows the usage of custom stickers from other servers.
     *
     * Channel Type(s): Text
     */
    USE_EXTERNAL_STICKERS(0x2000000000);
}

@Serializable(with = PermissionsSerializer::class)
public data class Permissions(val value: Long) {
    public operator fun contains(permission: Permission): Boolean {
        if (Permission.ADMINISTRATOR in value) {
            return true
        }

        return permission in value
    }

    public operator fun contains(permissions: Permissions): Boolean {
        return value and permissions.value == permissions.value
    }

    public operator fun plus(permissions: Long): Permissions = Permissions(value or permissions)

    public operator fun plus(permissions: Permissions): Permissions = plus(permissions.value)

    public operator fun plus(permissions: Collection<Permission>): Unit = permissions.forEach { plus(it.mask) }

    public operator fun plus(permission: Permission): Permissions = plus(permission.mask)

    public operator fun minus(permissions: Long): Permissions = Permissions(value and permissions.inv())

    public operator fun minus(permissions: Permissions): Permissions = minus(permissions.value)

    public operator fun minus(permissions: Collection<Permission>): Unit = permissions.forEach { minus(it.mask) }

    public operator fun minus(permission: Permission): Permissions = minus(permission.mask)

    override fun toString(): String = "Permissions($value) --> ${Permission.values().filter { it in value }.joinToString()}"

    public companion object {
        public val ALL: Permissions = of(*Permission.values())

        public val NONE: Permissions = Permissions(0)

        public fun of(vararg permissions: Permission): Permissions {
            return Permissions(permissions.map { permission -> permission.mask }.reduce { left, right -> left or right })
        }

        private operator fun Long.contains(permission: Permission) = this and permission.mask == permission.mask
    }
}

public object PermissionsSerializer : KSerializer<Permissions> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Permissions", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): Permissions = Permissions(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Permissions): Unit = encoder.encodeLong(value.value)
}
