package com.jessecorbett.diskord

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import com.jessecorbett.diskord.api.exceptions.DiscordException
import com.jessecorbett.diskord.api.exceptions.DiscordNotFoundException
import com.jessecorbett.diskord.api.common.Webhook
import com.jessecorbett.diskord.api.channel.CreateWebhook
import com.jessecorbett.diskord.api.webhook.PatchWebhook
import com.jessecorbett.diskord.api.webhook.WebhookSubmission
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.api.webhook.WebhookClient
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class WebhookClientIntegration {
    private val webhookChannel = "424046633253339136"

    private lateinit var webhook: Webhook
    private lateinit var webhookClient: WebhookClient

    @BeforeTest
    fun setup() {
        runBlocking {
            webhook = ChannelClient(BOT_TEST_TOKEN, webhookChannel).createWebhook(CreateWebhook(randomString()))
            webhookClient = WebhookClient(BOT_TEST_TOKEN, webhook.id)
        }
    }

    @AfterTest
    fun clean() {
        runBlocking {
            webhookClient.delete()
        }
    }

    @Test
    fun getWebhookTest() {
        runBlocking {
            webhookClient.getWebhook()
        }
    }

    @Test
    fun getWebhookWithToken() {
        runBlocking {
            webhookClient.getWebhook(webhook.token)
        }
    }

    @Test
    fun updateWebhookTest() {
        val originalName = webhook.defaultName

        runBlocking {
            webhookClient.update(PatchWebhook(randomString()))
            val newName = webhookClient.getWebhook().defaultName
            assertThat(originalName).isNotEqualTo(newName)

            webhookClient.update(PatchWebhook(originalName))
            val revertedName = webhookClient.getWebhook().defaultName
            assertThat(originalName).isEqualTo(revertedName)
        }
    }

    @Test
    fun updateWebhookWithTokenTest() {
        val originalName = webhook.defaultName

        runBlocking {
            webhookClient.update(PatchWebhook(randomString()), webhook.token)
            val newName = webhookClient.getWebhook().defaultName
            assertThat(originalName).isNotEqualTo(newName)

            webhookClient.update(PatchWebhook(originalName), webhook.token)
            val revertedName = webhookClient.getWebhook().defaultName
            assertThat(originalName).isEqualTo(revertedName)
        }
    }

    @Test
    fun deleteWebhookTest() {
        runBlocking {
            val webhookId =
                ChannelClient(BOT_TEST_TOKEN, webhookChannel).createWebhook(CreateWebhook(randomString())).id
            val client = WebhookClient(BOT_TEST_TOKEN, webhookId)

            client.getWebhook()
            client.delete()

            var deleted = false
            try {
                client.getWebhook()
            } catch (e: DiscordException) {
                assertThat(e).isInstanceOf(DiscordNotFoundException::class)
                deleted = true
            }

            assertThat(deleted).isTrue()
        }
    }

    @Test
    fun deleteWebhookWithTokenTest() {
        runBlocking {
            val ourWebhook = ChannelClient(BOT_TEST_TOKEN, webhookChannel).createWebhook(CreateWebhook(randomString()))
            val client = WebhookClient(BOT_TEST_TOKEN, ourWebhook.id)

            client.getWebhook()
            client.delete(ourWebhook.token)

            var deleted = false
            try {
                client.getWebhook()
            } catch (e: DiscordException) {
                assertThat(e).isInstanceOf(DiscordNotFoundException::class)
                deleted = true
            }

            assertThat(deleted).isTrue()
        }
    }

    @Test
    fun executeWebhookTest() {
        runBlocking {
            val content = randomString()
            val name = randomString()
            webhookClient.execute(webhook.token, WebhookSubmission(content, name))

            val channelClient = ChannelClient(BOT_TEST_TOKEN, webhookChannel)
            val message = channelClient.getMessage(channelClient.get().lastMessageId!!)

            assertThat(content).isEqualTo(message.content)
            assertThat(name).isEqualTo(message.author.username)
        }
    }
}
