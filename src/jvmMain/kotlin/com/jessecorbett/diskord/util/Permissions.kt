package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.model.*
import kotlinx.coroutines.runBlocking

// FIXME: Move this to commonMain source set once multi-platform refactoring is done.

fun Permissions.Companion.computePermissions(user: User, channel: Channel, clients: ClientStore) = runBlocking {
    val guildId = channel.guildId ?: TODO("Throw exception signifying the channel could not be identified")
    val guild = clients.guilds[guildId].get()
    val member = clients.guilds[guildId].getMember(user.id)
    val basePermissions = computeBasePermissions(member, guild)

    computeOverwrites(basePermissions, member, channel, guild)
}

private fun computeBasePermissions(member: GuildMember, guild: Guild): Permissions {
    if (member.user.id == guild.ownerId) {
        return Permissions.ALL
    }

    var permissions = Permissions(guild.permissions ?: 0)

    guild.roles.find { it.name == "@everyone" }?.also {
        permissions += it.permissions
    }

    guild.roles.filter { it.id in member.roleIds }.forEach {
        permissions += it.permissions
    }

    if (Permission.ADMINISTRATOR in permissions) {
        return Permissions.ALL
    }

    return permissions
}

private suspend fun computeOverwrites(
    basePermissions: Permissions,
    member: GuildMember,
    channel: Channel,
    guild: Guild
): Permissions {
    if (Permission.ADMINISTRATOR in basePermissions) {
        return Permissions.ALL
    }

    val everyone =
        guild.roles.find { it.name == "@everyone" } ?: TODO("Throw exception signifying that the everyone role couldn't be found (will this ever actually happen?)")

    return channel.permissionOverwrites.let { overwrites ->
        var deniedOverwrites = 0
        var allowedOverwrites = 0

        overwrites.find { it.type == OverwriteType.ROLE && it.id == everyone.id }?.also {
            deniedOverwrites -= it.denied
            allowedOverwrites += it.allowed
        }

        overwrites.filter { it.type == OverwriteType.ROLE && it.id in member.roleIds }.forEach {
            deniedOverwrites -= it.denied
            allowedOverwrites += it.allowed
        }

        overwrites.find { it.type == OverwriteType.MEMBER && it.id == member.user.id }?.also {
            deniedOverwrites -= it.denied
            allowedOverwrites += it.allowed
        }

        val permissions = basePermissions - deniedOverwrites
        permissions + allowedOverwrites
    }
}
