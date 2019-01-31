package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.*
import kotlinx.coroutines.runBlocking

// FIXME: Move this to commonMain source set once multi-platform refactoring is done.

fun computePermissions(user: User, channel: Channel, clients: ClientStore) = runBlocking {
    val guildId = channel.guildId ?: TODO("Throw exception signifying the channel could not be identified")
    val guild = clients.guilds[guildId].get()
    val member = clients.guilds[guildId].getMember(user.id)
    val basePermissions = computeBasePermissions(member, guild)

    computeOverwrites(basePermissions, member, channel, guild)
}

internal fun computeBasePermissions(member: GuildMember, guild: Guild): Permissions {
    if (member.user.id == guild.ownerId) {
        return Permissions.ALL
    }

    val permissions = Permissions(guild.permissions ?: 0)
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
