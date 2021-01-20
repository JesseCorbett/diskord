package com.jessecorbett.diskord

import assertk.assertThat
import assertk.assertions.*
import com.jessecorbett.diskord.api.exceptions.DiscordBadPermissionsException
import com.jessecorbett.diskord.api.exceptions.DiscordException
import com.jessecorbett.diskord.api.common.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.common.NotificationsLevel
import com.jessecorbett.diskord.api.common.VerificationLevel
import com.jessecorbett.diskord.api.global.CreateDM
import com.jessecorbett.diskord.api.global.CreateGuild
import com.jessecorbett.diskord.api.global.ModifyUser
import com.jessecorbett.diskord.api.global.GlobalClient
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.internal.client.RestClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Ignore
import kotlin.test.Test

class DiscordClientIntegration {
    private val tokensUser = "346444615831781376"
    private val discordClient = GlobalClient(RestClient.default(BOT_TEST_TOKEN))
    private val userForDM = "321775636798504962"

    @Test
    fun getApiGatewayTest() {
        val result = runBlocking { discordClient.getApiGateway() }
        assertThat(result.url).isNotEmpty()
    }

    @Test
    fun getBotApiGatewayTest() {
        val result = runBlocking { discordClient.getBotGateway() }
        assertThat(result.url).isNotEmpty()
        assertThat(result.shards).isGreaterThan(0)
    }

    @Test
    fun createGuildTest() {
        val voiceRegion = runBlocking { discordClient.getVoiceRegions()[0] }
        val createGuild = CreateGuild(
            randomString(), voiceRegion.id, base64Image, VerificationLevel.NONE, NotificationsLevel.ONLY_MENTIONS,
            ExplicitContentFilterLevel.DISABLED, emptyList(), emptyList()
        )

        val guild = runBlocking { discordClient.createGuild(createGuild) }

        val guildClient = GuildClient(guild.id, RestClient.default(BOT_TEST_TOKEN))
        runBlocking {
            guildClient.getGuild()
            guildClient.deleteChannel()
        }
        var guildDeleted = false
        try {
            runBlocking { guildClient.getGuild() }
        } catch (e: DiscordException) {
            assertThat(e).isInstanceOf(DiscordBadPermissionsException::class)
            guildDeleted = true
        }

        assertThat(guildDeleted).isTrue()
    }

    @Ignore
    @Test
    fun getInviteTest() {
        runBlocking {
            discordClient.getInvite(randomString())
        }
    }

    @Ignore
    @Test
    fun deleteInviteTest() {
        runBlocking {
            discordClient.deleteInvite(randomString())
        }
    }

    @Test
    fun getOurUserTest() {
        runBlocking {
            discordClient.getUser()
        }
    }

    @Test
    fun getUserTest() {
        runBlocking {
            val user = discordClient.getUser(tokensUser)
            assertThat(tokensUser).isEqualTo(user.id)
            assertThat(user.isBot).isEqualTo(true)
        }
    }

    @Ignore("This test is broken and needs to be fixed")
    @Test
    fun modifyUserTest() {
        runBlocking {
            val existingUser = discordClient.getUser()
            val existingUsername = existingUser.username

            discordClient.modifyUser(ModifyUser(randomString(), null))
            val modifiedUser = discordClient.getUser()
            assertThat(existingUser.username).isNotEqualTo(modifiedUser.username)

            discordClient.modifyUser(ModifyUser(existingUsername, null))
            val revertedUser = discordClient.getUser()
            assertThat(existingUsername).isEqualTo(revertedUser.username)
        }
    }

    @Test
    fun getGuildsTest() {
        runBlocking {
            val guilds = discordClient.getGuilds()
            assertThat(guilds).isNotEmpty()
            assertThat(guilds.size).isEqualTo(guilds.distinctBy { it.id }.size)
        }
    }

    @Test
    fun getDMsTest() {
        runBlocking { assertThat(discordClient.getDMs()).isEmpty() }
    }

    @Test
    fun createDMTest() {
        runBlocking { discordClient.createDM(CreateDM(userForDM)) }
    }

    @Ignore
    @Test
    fun getUserConnectionsTest() {
        runBlocking { discordClient.getUserConnections() }
        TODO("Find data to assert against")
    }

    @Test
    fun getVoiceRegionsTest() {
        runBlocking {
            val regions = discordClient.getVoiceRegions()
            assertThat(regions).isNotEmpty()
        }
    }
}
