package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.*

fun computePermissions(member: GuildMember, channel: Channel, guild: Guild) =
    computeOverwrites(computeBasePermissions(member, guild), member, channel, guild)

internal fun computeBasePermissions(member: GuildMember, guild: Guild): Permissions {
    if (member.user.id == guild.ownerId) {
        return Permissions.ALL
    }

    val permissions = guild.permissions ?: Permissions.NONE
    if (Permission.ADMINISTRATOR in permissions) {
        return Permissions.ALL
    }

    return permissions
}

internal fun computeOverwrites(
    basePermissions: Permissions,
    member: GuildMember,
    channel: Channel,
    guild: Guild
): Permissions {
    if (Permission.ADMINISTRATOR in basePermissions) {
        return Permissions.ALL
    }

    val everyone = guild.roles.find { it.name == "@everyone" }

    return channel.permissionOverwrites.let { overwrites ->
        var deniedOverwrites = 0
        var allowedOverwrites = 0

        if (everyone != null) {
            overwrites.find { it.type == OverwriteType.ROLE && it.id == everyone.id }?.also {
                deniedOverwrites = deniedOverwrites or it.denied
                allowedOverwrites = allowedOverwrites or it.allowed
            }
        }

        overwrites.filter { it.type == OverwriteType.ROLE && it.id in member.roleIds }.forEach {
            deniedOverwrites = deniedOverwrites or it.denied
            allowedOverwrites = allowedOverwrites or it.allowed
        }

        overwrites.find { it.type == OverwriteType.MEMBER && it.id == member.user.id }?.also {
            deniedOverwrites = deniedOverwrites or it.denied
            allowedOverwrites = allowedOverwrites or it.allowed
        }

        (basePermissions - deniedOverwrites) + allowedOverwrites
    }
}

private infix fun Int.or(permissions: Permissions) = this or permissions.value
