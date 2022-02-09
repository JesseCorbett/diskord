package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.interaction.command.Command
import com.jessecorbett.diskord.api.interaction.command.CommandPermissions
import com.jessecorbett.diskord.api.interaction.command.GuildCommandPermissions
import com.jessecorbett.diskord.api.interaction.command.PartialGuildCommandPermissions
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

@OptIn(DiskordInternals::class)
public class CommandClient(public val applicationId: String, client: RestClient) : RestClient by client {
    /**
     * FIXME - Document me!
     */
    public suspend fun getGlobalCommands(): List<Command> {
        return GET("/applications/$applicationId/commands").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createGlobalCommand(command: CreateCommand): Command {
        return POST("/applications/$applicationId/commands") { body = command }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGlobalCommand(commandId: String): Command {
        return GET("/applications/$applicationId/commands/$commandId").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateGlobalCommand(commandId: String, command: PatchCommand): Command {
        return PATCH("/applications/$applicationId/commands/$commandId") { body = command }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteGlobalCommand(commandId: String) {
        DELETE("/applications/$applicationId/commands/$commandId").receive<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkOverwriteGlobalCommands(commands: List<Command>): List<Command> {
        return PUT("/applications/$applicationId/commands") { body = commands }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommands(guildId: String): List<Command> {
        return GET("/applications/$applicationId/guilds/$guildId/commands").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createGuildCommand(guildId: String, command: CreateCommand): Command {
        return POST("/applications/$applicationId/guilds/$guildId/commands") { body = command }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommand(guildId: String, commandId: String): Command {
        return GET("/applications/$applicationId/guilds/$guildId/commands/$commandId").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateGuildCommand(guildId: String, commandId: String, command: PatchCommand): Command {
        return PATCH("/applications/$applicationId/guilds/$guildId/commands/$commandId") {
            body = command
        }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteGuildCommand(guildId: String, commandId: String) {
        DELETE("/applications/$applicationId/guilds/$guildId/commands/$commandId").receive<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkOverwriteGuildCommands(guildId: String, commands: List<Command>): List<Command> {
        return PUT("/applications/$applicationId/guilds/$guildId/commands") { body = commands }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommandsPermissions(guildId: String): List<GuildCommandPermissions> {
        return GET("/applications/$applicationId/guilds/$guildId/commands/permissions").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getGuildCommandPermissions(guildId: String, commandId: String): GuildCommandPermissions {
        return GET("/applications/$applicationId/guilds/$guildId/commands/$commandId/permissions").receive()
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
            body = permissions
        }.receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun bulkUpdateGuildCommandPermissions(
        guildId: String,
        permissions: List<PartialGuildCommandPermissions>
    ) {
        return PUT("/applications/$applicationId/guilds/$guildId/commands/permissions") {
            body = permissions
        }.receive()
    }
}
