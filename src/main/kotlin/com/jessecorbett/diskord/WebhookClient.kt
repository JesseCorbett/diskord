package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.models.Webhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission

class WebhookClient(token: String, val webhookId: String) : RestClient(token) {
    fun getWebhook() = getRequest("/webhooks/$webhookId").bodyAs(Webhook::class)

    fun getWebhookWithToken(webhookToken: String) = getRequest("/webhooks/$webhookId/$webhookToken").bodyAs(Webhook::class)

    fun updateWebhook(webhook: PatchWebhook) = patchRequest("/webhooks/$webhookId", webhook).bodyAs(Webhook::class)

    fun updateWebhookWithToken(webhookToken: String, webhook: PatchWebhook): Webhook {
        return patchRequest("/webhooks/$webhookId/$webhookToken", webhook).bodyAs(Webhook::class)
    }

    fun deleteWebhook() {
        deleteRequest("/webhooks/$webhookId")
    }

    fun deleteWebhookWithToken(webhookToken: String) {
        deleteRequest("/webhooks/$webhookId/$webhookToken")
    }

    fun executeWebhook(webhookToken: String, webhookSubmission: WebhookSubmission, waitForValidation: Boolean = false) {
        postRequest("/webhooks/$webhookId/$webhookToken?wait=$waitForValidation", webhookSubmission)
    }
}
