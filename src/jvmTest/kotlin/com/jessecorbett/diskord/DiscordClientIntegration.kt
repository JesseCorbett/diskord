package com.jessecorbett.diskord

import assertk.assertThat
import assertk.assertions.*
import com.jessecorbett.diskord.api.exception.DiscordBadPermissionsException
import com.jessecorbett.diskord.api.exception.DiscordException
import com.jessecorbett.diskord.api.model.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.model.NotificationsLevel
import com.jessecorbett.diskord.api.model.VerificationLevel
import com.jessecorbett.diskord.api.rest.CreateDM
import com.jessecorbett.diskord.api.rest.CreateGroupDM
import com.jessecorbett.diskord.api.rest.CreateGuild
import com.jessecorbett.diskord.api.rest.ModifyUser
import com.jessecorbett.diskord.api.rest.client.GlobalClient
import com.jessecorbett.diskord.api.rest.client.GuildClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Ignore
import kotlin.test.Test

class DiscordClientIntegration {
    private val tokensUser = "346444615831781376"
    private val discordClient = GlobalClient(BOT_TEST_TOKEN)
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

        val guildClient = GuildClient(BOT_TEST_TOKEN, guild.id)
        runBlocking {
            guildClient.get()
            guildClient.delete()
        }
        var guildDeleted = false
        try {
            runBlocking { guildClient.get() }
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
            assertThat(user.isBot).isTrue()
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
    fun createGroupDMTest() {
        runBlocking { discordClient.createGroupDM(CreateGroupDM(emptyList())) }
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
