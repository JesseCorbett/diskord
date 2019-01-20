package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.model.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.rest.client.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs

/**
 * A REST client for a a specific webhook.
 *
 * @param token The user's API token.
 * @param webhookId The id of the webhook.
 * @param userType The user type, assumed to be a bot.
 */
class WebhookClient(token: String, val webhookId: String, userType: DiscordUserType = DiscordUserType.BOT) : RestClient(token, userType) {

    /**
     * Get this webhook.
     *
     * @return This webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun getWebhook() = getRequest("/webhooks/$webhookId").bodyAs<Webhook>()

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
    suspend fun getWebhook(webhookToken: String) = getRequest("/webhooks/$webhookId/$webhookToken").bodyAs<Webhook>()

    /**
     * Update this webhook.
     *
     * @param webhook The patched webhook.
     *
     * @return The updated webhook.
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun update(webhook: PatchWebhook) = patchRequest("/webhooks/$webhookId", webhook).bodyAs<Webhook>()

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
    suspend fun update(webhook: PatchWebhook, webhookToken: String) = patchRequest("/webhooks/$webhookId/$webhookToken", webhook).bodyAs<Webhook>()

    /**
     * Delete this webhook.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete() = deleteRequest("/webhooks/$webhookId").close()

    /**
     * Delete this webhook using the secure token.
     *
     * Does not require authentication.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun delete(webhookToken: String) = deleteRequest("/webhooks/$webhookId/$webhookToken").close()


    /**
     * Execute the webhook.
     *
     * @param webhookToken The webhook's secure token.
     * @param webhookSubmission The post the webhook will make.
     * @param waitForValidation Wait for the message to be posted before the call returns. Defaults to false.
     *
     * @throws com.jessecorbett.diskord.api.exception.DiscordException
     */
    suspend fun execute(webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) =
            postRequest("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission).close()
}
