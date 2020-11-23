package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.model.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.rest.client.internal.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

/**
 * A REST client for a specific webhook
 *
 * @param webhookId The id of the webhook
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
class WebhookClient(
    val webhookId: String,
    client: RestClient
) : RestClient by client {

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
     * Does not require authentication and does not include the [com.jessecorbett.diskord.api.model.User].
     *
     * @param webhookToken The webhook's secure token.
     *
     * @return This webhook, minus the user.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhook(webhookToken: String): Webhook = GET("/webhooks/$webhookId", "/$webhookToken").receive()

    /**
     * Update this webhook.
     *
     * @param webhook The patched webhook.
     *
     * @return The updated webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(webhook: PatchWebhook): Webhook = PATCH("/webhooks/$webhookId") { body = webhook }.receive()

    /**
     * Update this webhook using the secure token.
     *
     * Does not require authentication and does not include the [com.jessecorbett.diskord.api.model.User].
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
    suspend fun execute(
        webhookToken: String,
        webhookSubmission: WebhookSubmission,
        waitForValidation: Boolean = false
    ): WebhookSubmission =  POST("/webhooks/$webhookId", "/$webhookToken?wait=$waitForValidation") { body = webhookSubmission }.receive()
}
