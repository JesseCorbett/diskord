package com.jessecorbett.diskord.api.rest.client

import assertk.assertThat
import assertk.assertions.isSameAs
import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.test.waitForTest
import com.jessecorbett.diskord.util.DiskordInternals
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.serialization.DeserializationStrategy
import kotlin.test.BeforeTest
import kotlin.test.Test

@DiskordInternals
class ChannelClientTest {
    @MockK
    private lateinit var restClient: RateLimitedRestClient

    @BeforeTest
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should get channel client`() {
        val token = "token"
        val channelId = "channelId"
        val channel = mockk<Channel>()

        coEvery {
            restClient.getRequest("/channels/$channelId", ofType<DeserializationStrategy<Channel>>())
        } returns channel

        val client = ChannelClient(token, channelId, client = restClient)

        assertThat(waitForTest { client.get() }).isSameAs(channel)
    }

    @Test
    fun `should update channel client`() {
        val token = "token"
        val channelId = "channelId"
        val sourceChannel = mockk<Channel>()
        val receivedChannel = mockk<Channel>()

        coEvery {
            restClient.putRequest(
                "/channels/$channelId",
                sourceChannel,
                ofType(),
                ofType<DeserializationStrategy<Channel>>()
            )
        } returns receivedChannel

        val client = ChannelClient(token, channelId, client = restClient)

        assertThat(waitForTest { client.update(sourceChannel) }).isSameAs(receivedChannel)
    }

    @Test
    fun `should delete channel client`() {
        val token = "token"
        val channelId = "channelId"

        coEvery {
            restClient.deleteRequest("/channels/$channelId", ofType<RateLimitInfo>())
        } just Runs

        val client = ChannelClient(token, channelId, client = restClient)
        waitForTest { client.delete() }

        coVerify {
            restClient.deleteRequest("/channels/$channelId", ofType<RateLimitInfo>())
        }

        confirmVerified(restClient)
    }
}
