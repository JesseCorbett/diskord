package com.jessecorbett.diskord.api.model

enum class Permission(val mask: Int) {
    CREATE_INSTANT_INVITE(0x00000001),
    KICK_MEMBERS(0x00000002),
    BAN_MEMBERS(0x00000004),
    ADMINISTRATOR(0x00000008),
    MANAGE_CHANNELS(0x00000010),
    MANAGE_GUILD(0x00000020),
    ADD_REACTIONS(0x00000040),
    VIEW_AUDIT_LOG(0x00000080),
    VIEW_CHANNEL(0x00000400),
    SEND_MESSAGES(0x00000800),
    SEND_TTS_MESSAGES(0x00001000),
    MANAGE_MESSAGES(0x00002000),
    EMBED_LINKS(0x00004000),
    ATTACH_FILES(0x00008000),
    READ_MESSAGE_HISTORY(0x00010000),
    MENTION_EVERYONE(0x00020000),
    USE_EXTERNAL_EMOJIS(0x00040000),
    CONNECT(0x00100000),
    SPEAK(0x00200000),
    MUTE_MEMBERS(0x00400000),
    DEAFEN_MEMBERS(0x00800000),
    MOVE_MEMBERS(0x01000000),
    USE_VAD(0x02000000),
    PRIORITY_SPEAKER(0x00000100),
    CHANGE_NICKNAME(0x04000000),
    MANAGE_NICKNAMES(0x08000000),
    MANAGE_ROLES(0x10000000),
    MANAGE_WEBHOOKS(0x20000000),
    MANAGE_EMOJIS(0x40000000);
}

class Permissions internal constructor(val value: Int) {
    operator fun contains(permission: Permission): Boolean {
        if (Permission.ADMINISTRATOR in value) {
            return true
        }

        return permission in value
    }

    companion object {
        val ALL = Permissions.of(
            Permission.CREATE_INSTANT_INVITE,
            Permission.KICK_MEMBERS,
            Permission.BAN_MEMBERS,
            Permission.ADMINISTRATOR,
            Permission.MANAGE_CHANNELS,
            Permission.MANAGE_GUILD,
            Permission.ADD_REACTIONS,
            Permission.VIEW_AUDIT_LOG,
            Permission.VIEW_CHANNEL,
            Permission.SEND_MESSAGES,
            Permission.SEND_TTS_MESSAGES,
            Permission.MANAGE_MESSAGES,
            Permission.EMBED_LINKS,
            Permission.ATTACH_FILES,
            Permission.READ_MESSAGE_HISTORY,
            Permission.MENTION_EVERYONE,
            Permission.USE_EXTERNAL_EMOJIS,
            Permission.CONNECT,
            Permission.SPEAK,
            Permission.MUTE_MEMBERS,
            Permission.DEAFEN_MEMBERS,
            Permission.MOVE_MEMBERS,
            Permission.USE_VAD,
            Permission.PRIORITY_SPEAKER,
            Permission.CHANGE_NICKNAME,
            Permission.MANAGE_NICKNAMES,
            Permission.MANAGE_ROLES,
            Permission.MANAGE_WEBHOOKS,
            Permission.MANAGE_EMOJIS
        )

        fun of(vararg permissions: Permission) =
            Permissions(permissions.map { permission -> permission.mask }.reduce { left, right -> left or right })
    }
}

private operator fun Int.contains(permission: Permission) = this and permission.mask == permission.mask
