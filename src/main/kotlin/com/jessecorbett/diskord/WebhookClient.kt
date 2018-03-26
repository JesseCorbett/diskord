package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.internal.DiscordToken
import com.jessecorbett.diskord.internal.RestClient
import com.jessecorbett.diskord.internal.bodyAs

class WebhookClient(token: DiscordToken, val webhookId: String) : RestClient(token) {
    fun getWebhook() = getRequest("/webhooks/$webhookId").bodyAs(Webhook::class)

    fun getWebhook(webhookToken: String) = getRequest("/webhooks/$webhookId/$webhookToken").bodyAs(Webhook::class)

    fun update(webhook: PatchWebhook) = patchRequest("/webhooks/$webhookId", webhook).bodyAs(Webhook::class)

    fun update(webhook: PatchWebhook, webhookToken: String) = patchRequest("/webhooks/$webhookId/$webhookToken", webhook).bodyAs(Webhook::class)

    fun delete() = deleteRequest("/webhooks/$webhookId").close()

    fun delete(webhookToken: String) = deleteRequest("/webhooks/$webhookId/$webhookToken").close()

    fun execute(webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) =
            postRequest("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission).close()
}
