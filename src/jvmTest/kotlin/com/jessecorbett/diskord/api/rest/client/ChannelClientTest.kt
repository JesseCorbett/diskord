package com.jessecorbett.diskord.api.rest.client

import io.mockk.clearAllMocks
import kotlin.test.BeforeTest

class ChannelClientTest {
    @BeforeTest
    fun init() {
        clearAllMocks()
    }

    fun `should get channel client`() {
        val server = MockWebServer()
    }
}
