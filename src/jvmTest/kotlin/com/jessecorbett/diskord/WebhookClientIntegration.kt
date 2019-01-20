package com.jessecorbett.diskord

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import com.jessecorbett.diskord.api.exception.DiscordException
import com.jessecorbett.diskord.api.exception.DiscordNotFoundException
import com.jessecorbett.diskord.api.model.Webhook
import com.jessecorbett.diskord.api.rest.CreateWebhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.rest.client.ChannelClient
import com.jessecorbett.diskord.api.rest.client.WebhookClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WebhookClientIntegration {
    private val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DtS9xw.vqBteMXax6dwTrQ8ghJD5QyKX_8"
    private val webhookChannel = "424046633253339136"

    private lateinit var webhook: Webhook
    private lateinit var webhookClient: WebhookClient

    @BeforeEach fun setup() {
        runBlocking {
            webhook = ChannelClient(token, webhookChannel).createWebhook(CreateWebhook(randomString()))
            webhookClient = WebhookClient(token, webhook.id)
        }
    }

    @AfterEach fun clean() {
        runBlocking {
            webhookClient.delete()
        }
    }

    @Test fun getWebhookTest() {
        runBlocking {
            webhookClient.getWebhook()
        }
    }

    @Test fun getWebhookWithToken() {
        runBlocking {
            webhookClient.getWebhook(webhook.token)
        }
    }

    @Test fun updateWebhookTest() {
        val originalName = webhook.defaultName

        runBlocking {
            webhookClient.update(PatchWebhook(randomString()))
            val newName = webhookClient.getWebhook().defaultName
            assert(originalName).isNotEqualTo(newName)

            webhookClient.update(PatchWebhook(originalName))
            val revertedName = webhookClient.getWebhook().defaultName
            assert(originalName).isEqualTo(revertedName)
        }
    }

    @Test fun updateWebhookWithTokenTest() {
        val originalName = webhook.defaultName

        runBlocking {
            webhookClient.update(PatchWebhook(randomString()), webhook.token)
            val newName = webhookClient.getWebhook().defaultName
            assert(originalName).isNotEqualTo(newName)

            webhookClient.update(PatchWebhook(originalName), webhook.token)
            val revertedName = webhookClient.getWebhook().defaultName
            assert(originalName).isEqualTo(revertedName)
        }
    }

    @Test fun deleteWebhookTest() {
        runBlocking {
            val webhookId = ChannelClient(token, webhookChannel).createWebhook(CreateWebhook(randomString())).id
            val client = WebhookClient(token, webhookId)

            client.getWebhook()
            client.delete()

            var deleted = false
            try {
                client.getWebhook()
            } catch (e: DiscordException) {
                assert(e).isInstanceOf(DiscordNotFoundException::class)
                deleted = true
            }

            assert(deleted).isTrue()
        }
    }

    @Test fun deleteWebhookWithTokenTest() {
        runBlocking {
            val ourWebhook = ChannelClient(token, webhookChannel).createWebhook(CreateWebhook(randomString()))
            val client = WebhookClient(token, ourWebhook.id)

            client.getWebhook()
            client.delete(ourWebhook.token)

            var deleted = false
            try {
                client.getWebhook()
            } catch (e: DiscordException) {
                assert(e).isInstanceOf(DiscordNotFoundException::class)
                deleted = true
            }

            assert(deleted).isTrue()
        }
    }

    @Test fun executeWebhookTest() {
        runBlocking {
            val content = randomString()
            val name = randomString()
            webhookClient.execute(webhook.token, WebhookSubmission(content, name))

            val channelClient = ChannelClient(token, webhookChannel)
            val message = channelClient.getMessage(channelClient.get().lastMessageId!!)

            assert(content).isEqualTo(message.content)
            assert(name).isEqualTo(message.author.username)
        }
    }
}
