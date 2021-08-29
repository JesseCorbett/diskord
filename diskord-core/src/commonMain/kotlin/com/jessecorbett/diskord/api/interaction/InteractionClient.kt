package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

@OptIn(DiskordInternals::class)
public class InteractionClient(public val applicationId: String, client: RestClient) : RestClient by client {
    /**
     * FIXME - Document me!
     */
    public suspend fun getGlobalCommands(): List<Command> {
        return GET("/applications/$applicationId/commands").receive()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createGlobalCommand(command: CreateGlobalCommand): Command {
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
    public suspend fun updateGlobalCommand(commandId: String, command: PatchGlobalCommand): Command {
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
}
