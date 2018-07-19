package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.Webhook
import com.jessecorbett.diskord.api.rest.CreateWebhook
import com.jessecorbett.diskord.api.rest.PatchWebhook
import com.jessecorbett.diskord.api.rest.WebhookSubmission
import com.jessecorbett.diskord.api.exception.DiscordException
import com.jessecorbett.diskord.api.exception.DiscordNotFoundException
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WebhookClientIntegration {
    private val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DYuHdA.wVgVrSJ0DqO0RfUHwm9xeZStPNY"
    private val webhookChannel = "424046633253339136"

    private lateinit var webhook: Webhook
    private lateinit var webhookClient: WebhookClient

    @Before fun setup() {
        runBlocking {
            webhook = ChannelClient(token, webhookChannel).createWebhook(CreateWebhook(randomString()))
            webhookClient = WebhookClient(token, webhook.id)
        }
    }

    @After fun clean() {
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
            Assert.assertNotEquals(originalName, newName)

            webhookClient.update(PatchWebhook(originalName))
            val revertedName = webhookClient.getWebhook().defaultName
            Assert.assertEquals(originalName, revertedName)
        }
    }

    @Test fun updateWebhookWithTokenTest() {
        val originalName = webhook.defaultName

        runBlocking {
            webhookClient.update(PatchWebhook(randomString()), webhook.token)
            val newName = webhookClient.getWebhook().defaultName
            Assert.assertNotEquals(originalName, newName)

            webhookClient.update(PatchWebhook(originalName), webhook.token)
            val revertedName = webhookClient.getWebhook().defaultName
            Assert.assertEquals(originalName, revertedName)
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
                Assert.assertTrue(e is DiscordNotFoundException)
                deleted = true
            }

            Assert.assertTrue(deleted)
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
                Assert.assertTrue(e is DiscordNotFoundException)
                deleted = true
            }

            Assert.assertTrue(deleted)
        }
    }

    @Test fun executeWebhookTest() {
        runBlocking {
            val content = randomString()
            val name = randomString()
            webhookClient.execute(webhook.token, WebhookSubmission(content, name))

            val channelClient = ChannelClient(token, webhookChannel)
            val message = channelClient.getMessage(channelClient.getChannel().lastMessageId!!)

            Assert.assertEquals(content, message.content)
            Assert.assertEquals(name, message.author!!.username)
        }
    }
}
