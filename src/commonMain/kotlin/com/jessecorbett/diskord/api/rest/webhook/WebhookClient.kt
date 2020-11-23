package com.jessecorbett.diskord.api.rest.webhook

import com.jessecorbett.diskord.api.common.Webhook
import com.jessecorbett.diskord.api.rest.diskordinternal.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

/**
 * A REST client for a specific webhook
 *
 * @param webhookId The id of the webhook
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
class WebhookClient(val webhookId: String, client: RestClient) : RestClient by client {

    /**
     * Get this webhook.
     *
     * @return This webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhook(): Webhook = GET("/webhooks/$webhookId").receive()

    /**
     * Get this webhook using the secure token.
     *
     * Does not require authentication and does not include the [com.jessecorbett.diskord.api.common.User].
     *
     * @param webhookToken The webhook's secure token.
     *
     * @return This webhook, minus the user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhook(webhookToken: String): Webhook {
        return GET("/webhooks/$webhookId", "/$webhookToken").receive()
    }

    /**
     * Update this webhook.
     *
     * @param webhook The patched webhook.
     *
     * @return The updated webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(webhook: PatchWebhook): Webhook {
        return PATCH("/webhooks/$webhookId") { body = webhook }.receive()
    }

    /**
     * Update this webhook using the secure token.
     *
     * Does not require authentication and does not include the [com.jessecorbett.diskord.api.common.User].
     *
     * @param webhook The patched webhook.
     *
     * @return The updated webhook, minus the user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(webhook: PatchWebhook, webhookToken: String): Webhook {
        return PATCH("/webhooks/$webhookId", "/$webhookToken") { body = webhook }.receive()
    }

    /**
     * Delete this webhook.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete(): Unit = DELETE("/webhooks/$webhookId").receive()

    /**
     * Delete this webhook using the secure token.
     *
     * Does not require authentication.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete(webhookToken: String): Unit = DELETE("/webhooks/$webhookId", "/$webhookToken").receive()


    /**
     * Execute the webhook.
     *
     * @param webhookToken The webhook's secure token.
     * @param webhookSubmission The post the webhook will make.
     * @param waitForValidation Wait for the message to be posted before the call returns. Defaults to false.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun execute(webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false): WebhookSubmission {
        return POST("/webhooks/$webhookId", "/$webhookToken?wait=$waitForValidation") { body = webhookSubmission }.receive()
    }
}
