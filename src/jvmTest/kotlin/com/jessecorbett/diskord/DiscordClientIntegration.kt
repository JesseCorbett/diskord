package com.jessecorbett.diskord

import assertk.assert
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
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.rest.client.GuildClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class DiscordClientIntegration {
    private val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DtS9xw.vqBteMXax6dwTrQ8ghJD5QyKX_8"
    private val tokensUser = "346444615831781376"
    private val discordClient = DiscordClient(token)
    private val userForDM = "321775636798504962"

    @Test fun getApiGatewayTest() {
        val result = runBlocking { discordClient.getApiGateway() }
        assert(result.url).isNotEmpty()
    }

    @Test fun getBotApiGatewayTest() {
        val result = runBlocking { discordClient.getBotGateway() }
        assert(result.url).isNotEmpty()
        assert(result.shards).isGreaterThan(0)
    }

    @Test fun createGuildTest() {
        val voiceRegion = runBlocking { discordClient.getVoiceRegions()[0] }
        val createGuild = CreateGuild(randomString(), voiceRegion.id, base64Image, VerificationLevel.NONE, NotificationsLevel.ONLY_MENTIONS,
                ExplicitContentFilterLevel.DISABLED, emptyList(), emptyList())

        val guild = runBlocking { discordClient.createGuild(createGuild) }

        val guildClient = GuildClient(token, guild.id)
        runBlocking {
            guildClient.get()
            guildClient.delete()
        }
        var guildDeleted = false
        try {
            runBlocking { guildClient.get() }
        } catch (e: DiscordException) {
            assert(e).isInstanceOf(DiscordBadPermissionsException::class)
            guildDeleted = true
        }

        assert(guildDeleted).isTrue()
    }

    @Disabled
    @Test fun getInviteTest() {
        runBlocking {
            discordClient.getInvite(randomString())
        }
    }

    @Disabled
    @Test fun deleteInviteTest() {
        runBlocking {
            discordClient.deleteInvite(randomString())
        }
    }

    @Test fun getOurUserTest() {
        runBlocking {
            discordClient.getUser()
        }
    }

    @Test fun getUserTest() {
        runBlocking {
            val user = discordClient.getUser(tokensUser)
            assert(tokensUser).isEqualTo(user.id)
            assert(user.isBot).isTrue()
        }
    }

    @Disabled("This test is broken and needs to be fixed")
    @Test fun modifyUserTest() {
        runBlocking {
            val existingUser = discordClient.getUser()
            val existingUsername = existingUser.username

            discordClient.modifyUser(ModifyUser(randomString()))
            val modifiedUser = discordClient.getUser()
            assert(existingUser.username).isNotEqualTo(modifiedUser.username)

            discordClient.modifyUser(ModifyUser(existingUsername))
            val revertedUser = discordClient.getUser()
            assert(existingUsername).isEqualTo(revertedUser.username)
        }
    }

    @Test fun getGuildsTest() {
        runBlocking {
            val guilds = discordClient.getGuilds()
            assert(guilds).isNotEmpty()
            assert(guilds.size).isEqualTo(guilds.distinctBy { it.id }.size)
        }
    }

    @Test fun getDMsTest() {
        runBlocking { assert(discordClient.getDMs()).isEmpty() }
    }

    @Test fun createDMTest() {
        runBlocking { discordClient.createDM(CreateDM(userForDM)) }
    }

    @Disabled
    @Test fun createGroupDMTest() {
        runBlocking { discordClient.createGroupDM(CreateGroupDM(emptyList())) }
    }

    @Disabled
    @Test fun getUserConnectionsTest() {
        runBlocking { discordClient.getUserConnections() }
        TODO("Find data to assert against")
    }

    @Test fun getVoiceRegionsTest() {
        runBlocking {
            val regions = discordClient.getVoiceRegions()
            assert(regions).isNotEmpty()
        }
    }
}
