package com.jessecorbett.diskord.api.rest.client

import com.jessecorbett.diskord.api.model.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.DiscordUserType
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
    suspend fun getWebhook() = getRequest("/webhooks/$webhookId").bodyAs<Webhook>()

    suspend fun getWebhook(webhookToken: String) = getRequest("/webhooks/$webhookId/$webhookToken").bodyAs<Webhook>()

    suspend fun update(webhook: PatchWebhook) = patchRequest("/webhooks/$webhookId", webhook).bodyAs<Webhook>()

    suspend fun update(webhook: PatchWebhook, webhookToken: String) = patchRequest("/webhooks/$webhookId/$webhookToken", webhook).bodyAs<Webhook>()

    suspend fun delete() = deleteRequest("/webhooks/$webhookId").close()

    suspend fun delete(webhookToken: String) = deleteRequest("/webhooks/$webhookId/$webhookToken").close()

    suspend fun execute(webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) =
            postRequest("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission).close()
}
