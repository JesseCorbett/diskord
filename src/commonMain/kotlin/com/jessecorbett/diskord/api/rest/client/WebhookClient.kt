package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.model.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.rest.client.internal.RateLimitedClient

/**
 * A REST client for a a specific webhook.
 *
 * @param token The user's API token.
 * @param webhookId The id of the webhook.
 * @param userType The user type, assumed to be a bot.
 */
class WebhookClient(token: String, val webhookId: String, userType: DiscordUserType = DiscordUserType.BOT) :
    RateLimitedClient(token, userType) {

    /**
     * Get this webhook.
     *
     * @return This webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhook() = getRequest("/webhooks/$webhookId", Webhook.serializer())

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
    suspend fun getWebhook(webhookToken: String) =
        getRequest("/webhooks/$webhookId/$webhookToken", Webhook.serializer())

    /**
     * Update this webhook.
     *
     * @param webhook The patched webhook.
     *
     * @return The updated webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(webhook: PatchWebhook) =
        patchRequest("/webhooks/$webhookId", webhook, PatchWebhook.serializer(), Webhook.serializer())

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
    suspend fun update(webhook: PatchWebhook, webhookToken: String) =
        patchRequest("/webhooks/$webhookId/$webhookToken", webhook, PatchWebhook.serializer(), Webhook.serializer())

    /**
     * Delete this webhook.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete() = deleteRequest("/webhooks/$webhookId")

    /**
     * Delete this webhook using the secure token.
     *
     * Does not require authentication.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete(webhookToken: String) = deleteRequest("/webhooks/$webhookId/$webhookToken")


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
    ) = postRequest(
        "/webhooks/$webhookId/$webhookToken?wait=$waitForValidation",
        webhookSubmission,
        WebhookSubmission.serializer()
    )
}
