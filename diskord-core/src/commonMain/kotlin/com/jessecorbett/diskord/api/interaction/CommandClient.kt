package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandPermissions
import com.jessecorbett.diskord.api.interaction.command.GuildCommandPermissions
import com.jessecorbett.diskord.api.interaction.command.PartialGuildCommandPermissions
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*
import io.ktor.client.request.setBody

@OptIn(DiskordInternals::class)
public class CommandClient(public val applicationId: String, client: RestClient) : RestClient by client {
    /**
     * FIXME - Document me!
     */
    public suspend fun getGlobalCommands(): List<Command> {
        return GET("/applications/$applicationId/commands").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createGlobalCommand(command: CreateCommand): Command {
        return POST("/applications/$applicationId/commands") { setBody(command) }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGlobalCommand(commandId: String): Command {
        return GET("/applications/$applicationId/commands/$commandId").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateGlobalCommand(commandId: String, command: PatchCommand): Command {
        return PATCH("/applications/$applicationId/commands/$commandId") { setBody(command) }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteGlobalCommand(commandId: String) {
        DELETE("/applications/$applicationId/commands/$commandId").body<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkOverwriteGlobalCommands(commands: List<Command>): List<Command> {
        return PUT("/applications/$applicationId/commands") { setBody(commands) }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommands(guildId: String): List<Command> {
        return GET("/applications/$applicationId/guilds/$guildId/commands").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createGuildCommand(guildId: String, command: CreateCommand): Command {
        return POST("/applications/$applicationId/guilds/$guildId/commands") { setBody(command) }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommand(guildId: String, commandId: String): Command {
        return GET("/applications/$applicationId/guilds/$guildId/commands/$commandId").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateGuildCommand(guildId: String, commandId: String, command: PatchCommand): Command {
        return PATCH("/applications/$applicationId/guilds/$guildId/commands/$commandId") {
            setBody(command)
        }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteGuildCommand(guildId: String, commandId: String) {
        DELETE("/applications/$applicationId/guilds/$guildId/commands/$commandId").body<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkOverwriteGuildCommands(guildId: String, commands: List<Command>): List<Command> {
        return PUT("/applications/$applicationId/guilds/$guildId/commands") { setBody(commands) }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommandsPermissions(guildId: String): List<GuildCommandPermissions> {
        return GET("/applications/$applicationId/guilds/$guildId/commands/permissions").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommandPermissions(guildId: String, commandId: String): GuildCommandPermissions {
        return GET("/applications/$applicationId/guilds/$guildId/commands/$commandId/permissions").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateGuildCommandPermissions(
        guildId: String,
        commandId: String,
        permissions: List<CommandPermissions>
    ) {
        return PUT("/applications/$applicationId/guilds/$guildId/commands/$commandId/permissions") {
            setBody(permissions)
        }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkUpdateGuildCommandPermissions(
        guildId: String,
        permissions: List<PartialGuildCommandPermissions>
    ) {
        return PUT("/applications/$applicationId/guilds/$guildId/commands/permissions") {
            setBody(permissions)
        }.body()
    }
}
