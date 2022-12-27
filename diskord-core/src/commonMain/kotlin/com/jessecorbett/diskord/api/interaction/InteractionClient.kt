package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.interaction.callback.InteractionResponse
import com.jessecorbett.diskord.api.webhook.CreateWebhookMessage
import com.jessecorbett.diskord.api.webhook.PatchWebhookMessage
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*
import io.ktor.client.request.setBody

@OptIn(DiskordInternals::class)
public class InteractionClient(
    public val applicationId: String,
    public val interactionToken: String,
    client: RestClient
) : RestClient by client {
    /**
     * FIXME - Document me!
     */
    public suspend fun createInteractionResponse(
        interactionId: String,
        interactionResponse: InteractionResponse
    ) {
        POST("/interactions/$interactionId/$interactionToken/callback") {
            setBody(interactionResponse)
        }.body<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getOriginalInteractionResponse(): Message {
        return GET("/webhooks/$applicationId/$interactionToken/messages/@original").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateOriginalInteractionResponse(message: PatchWebhookMessage): Message {
        return PATCH("/webhooks/$applicationId/$interactionToken/messages/@original") {
            setBody(message)
        }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteOriginalInteractionResponse() {
        DELETE("/webhooks/$applicationId/$interactionToken/messages/@original").body<Unit>()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun createFollowupMessage(message: CreateWebhookMessage): Message {
        return POST("/webhooks/$applicationId/$interactionToken") {
            setBody(message)
        }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun getFollowupMessage(messageId: String): Message {
        return GET("/webhooks/$applicationId/$interactionToken/messages/$messageId").body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun updateFollowupMessage(messageId: String, message: PatchWebhookMessage): Message {
        return PATCH("/webhooks/$applicationId/$interactionToken/messages/$messageId") {
            setBody(message)
        }.body()
    }

    /**
     * FIXME - Document me!
     */
    public suspend fun deleteFollowupMessage(messageId: String) {
        DELETE("/webhooks/$applicationId/$interactionToken/messages/$messageId").body<Unit>()
    }
}
