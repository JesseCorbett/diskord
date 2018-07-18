package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.ExplicitContentFilterLevel
import com.jessecorbett.diskord.api.NotificationsLevel
import com.jessecorbett.diskord.api.VerificationLevel
import com.jessecorbett.diskord.api.rest.CreateDM
import com.jessecorbett.diskord.api.rest.CreateGroupDM
import com.jessecorbett.diskord.api.rest.CreateGuild
import com.jessecorbett.diskord.api.rest.ModifyUser
import com.jessecorbett.diskord.exception.DiscordBadPermissionsException
import com.jessecorbett.diskord.exception.DiscordException
import com.jessecorbett.diskord.internal.DiscordToken
import com.jessecorbett.diskord.internal.TokenType
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

class DiscordClientIntegration {
    private val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DYuHdA.wVgVrSJ0DqO0RfUHwm9xeZStPNY"
    private val tokensUser = "346444615831781376"
    private val discordClient = DiscordClient(DiscordToken(token, TokenType.BOT))
    private val userForDM = "321775636798504962"

    @Test fun getApiGatewayTest() {
        val result = runBlocking { discordClient.getApiGateway() }
        Assert.assertTrue(result.url.isNotBlank())
    }

    @Test fun getBotApiGatewayTest() {
        val result = runBlocking { discordClient.getBotGateway() }
        Assert.assertTrue(result.url.isNotBlank())
        Assert.assertTrue(result.shards > 0)
    }

    @Test fun createGuildTest() {
        val voiceRegion = runBlocking { discordClient.getVoiceRegions()[0] }
        val createGuild = CreateGuild(randomString(), voiceRegion.id, base64Image, VerificationLevel.NONE, NotificationsLevel.ONLY_MENTIONS,
                ExplicitContentFilterLevel.DISABLED, emptyList(), emptyList())

        val guild = runBlocking { discordClient.createGuild(createGuild) }

        val guildClient = GuildClient(DiscordToken(token, TokenType.BOT), guild.id)
        runBlocking {
            guildClient.getGuild()
            guildClient.delete()
        }
        var guildDeleted = false
        try {
            runBlocking { guildClient.getGuild() }
        } catch (e: DiscordException) {
            Assert.assertTrue(e is DiscordBadPermissionsException)
            guildDeleted = true
        }
        Assert.assertTrue(guildDeleted)
    }

    @Ignore
    @Test fun getInviteTest() {
        runBlocking {
            discordClient.getInvite(randomString())
        }
    }

    @Ignore
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
            Assert.assertEquals(tokensUser, user.id)
            Assert.assertTrue(user.isBot)
        }
    }

    @Test fun modifyUserTest() {
        runBlocking {
            val existingUser = discordClient.getUser()
            val existingUsername = existingUser.username

            discordClient.modifyUser(ModifyUser(randomString()))
            val modifiedUser = discordClient.getUser()
            Assert.assertNotEquals(existingUser.username, modifiedUser.username)

            discordClient.modifyUser(ModifyUser(existingUsername))
            val revertedUser = discordClient.getUser()
            Assert.assertEquals(existingUsername, revertedUser.username)
        }
    }

    @Test fun getGuildsTest() {
        runBlocking {
            val guilds = discordClient.getGuilds()
            Assert.assertNotEquals(0, guilds.size)
            Assert.assertEquals(guilds.size, guilds.distinctBy { it.id }.size)
        }
    }

    @Test fun getDMsTest() {
        runBlocking { Assert.assertTrue(discordClient.getDMs().isEmpty()) }
    }

    @Test fun createDMTest() {
        runBlocking { discordClient.createDM(CreateDM(userForDM)) }
    }

    @Ignore
    @Test fun createGroupDMTest() {
        runBlocking { discordClient.createGroupDM(CreateGroupDM(emptyList())) }
    }

    @Ignore
    @Test fun getUserConnectionsTest() {
        runBlocking { discordClient.getUserConnections() }
        TODO("Find data to assert against")
    }

    @Test fun getVoiceRegionsTest() {
        runBlocking {
            val regions = discordClient.getVoiceRegions()
            Assert.assertFalse(regions.isEmpty())
        }
    }
}
