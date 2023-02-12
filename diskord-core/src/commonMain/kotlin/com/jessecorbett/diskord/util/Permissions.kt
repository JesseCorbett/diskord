package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.GuildChannel
import com.jessecorbett.diskord.api.common.GuildMember
import com.jessecorbett.diskord.api.common.OverwriteType
import com.jessecorbett.diskord.api.common.Permission
import com.jessecorbett.diskord.api.common.Permissions
import com.jessecorbett.diskord.api.common.User
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.internal.client.RestClient

/**
 * Compute the permissions for the given user in the channel.  Takes into account the permission overwrites and
 * whether the user is an administrator.
 *
 * @param user the user whose permissions should be computed
 * @param channel the channel to compute the permissions for
 * @param restClient the client to use for lookup
 */
public suspend fun computePermissions(user: User, channel: GuildChannel, restClient: RestClient): Permissions {
    val client = GuildClient(channel.guildId!!, restClient) // TODO: Resolve npe

    return computePermissions(client.getMember(user.id), channel, client.getGuild())
}

/**
 * Compute the permissions for the given guild member in the channel in the
 * guild.  Takes into account the permission overwrites and whether or not
 * the user is an administrator.
 *
 * @param member the member whose permissions should be computed
 * @param channel the channel to compute the permissions for
 * @param guild the guild which owns the channel
 */
public fun computePermissions(member: GuildMember, channel: GuildChannel, guild: Guild): Permissions {
    return computeOverwrites(computeBasePermissions(member, guild), member, channel, guild)
}

/**
 * Compute the base permissions for the given guild member in the guild.  If
 * the member is an administrator then all permissions are granted.
 *
 * @param member the member whose permissions should be computed
 * @param guild the guild which the user is a member of
 */
internal fun computeBasePermissions(member: GuildMember, guild: Guild): Permissions {
    if (member.user?.id == guild.ownerId) {
        return Permissions.ALL
    }

    val permissions = guild.permissions ?: Permissions.NONE
    if (Permission.ADMINISTRATOR in permissions) {
        return Permissions.ALL
    }

    return permissions
}

/**
 * Computes the permissions for the given guild member using the specified
 * base permissions and the overwrites pulled from the channel and guild.
 *
 * @param basePermissions the pre-computed base permissions
 * @param member the member whose overwrites should be computed
 * @param channel the channel to compute the overwrites for
 * @param guild the guild which owns the channel
 */
internal fun computeOverwrites(
    basePermissions: Permissions,
    member: GuildMember,
    channel: GuildChannel,
    guild: Guild
): Permissions {
    if (Permission.ADMINISTRATOR in basePermissions) {
        return Permissions.ALL
    }

    val everyone = guild.roles.find { it.name == "@everyone" }

    return channel.permissionOverwrites.let { overwrites ->
        var deniedOverwrites = 0L
        var allowedOverwrites = 0L

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

        overwrites.find { it.type == OverwriteType.MEMBER && it.id == member.user?.id }?.also {
            deniedOverwrites = deniedOverwrites or it.denied
            allowedOverwrites = allowedOverwrites or it.allowed
        }

        (basePermissions - deniedOverwrites) + allowedOverwrites
    }
}

private infix fun Long.or(permissions: Permissions) = this or permissions.value
