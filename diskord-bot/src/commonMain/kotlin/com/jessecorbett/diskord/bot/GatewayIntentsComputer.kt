package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.DispatchBase
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent

internal class GatewayIntentsComputer : EventDispatcher<Unit> {
    val intents: MutableList<GatewayIntent> = mutableListOf()

    override suspend fun onReady(handler: suspend DispatchBase.(Ready) -> Unit) {}

    override suspend fun onResume(handler: suspend DispatchBase.(Resumed) -> Unit) {}

    override suspend fun onChannelCreate(handler: suspend DispatchBase.(Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onChannelUpdate(handler: suspend DispatchBase.(Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onChannelDelete(handler: suspend DispatchBase.(Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onChannelPinsUpdate(handler: suspend DispatchBase.(ChannelPinUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override suspend fun onGuildCreate(handler: suspend DispatchBase.(Guild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildUpdate(handler: suspend DispatchBase.(Guild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildDelete(handler: suspend DispatchBase.(UnavailableGuild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildBanAdd(handler: suspend DispatchBase.(GuildBan) -> Unit) {
        intents += GatewayIntent.GUILD_BANS
    }

    override suspend fun onGuildBanRemove(handler: suspend DispatchBase.(GuildBan) -> Unit) {
        intents += GatewayIntent.GUILD_BANS
    }

    override suspend fun onGuildEmojiUpdate(handler: suspend DispatchBase.(GuildEmojiUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_EMOJIS
    }

    override suspend fun onGuildIntegrationsUpdate(handler: suspend DispatchBase.(GuildIntegrationUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_INTEGRATIONS
    }

    override suspend fun onGuildMemberAdd(handler: suspend DispatchBase.(GuildMemberAdd) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override suspend fun onGuildMemberUpdate(handler: suspend DispatchBase.(GuildMemberUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override suspend fun onGuildMemberRemove(handler: suspend DispatchBase.(GuildMemberRemove) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override suspend fun onGuildMembersChunk(handler: suspend DispatchBase.(GuildMembersChunk) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS // Not technically necessary, but safe to assume by default
    }

    override suspend fun onGuildRoleCreate(handler: suspend DispatchBase.(GuildRoleCreate) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildRoleUpdate(handler: suspend DispatchBase.(GuildRoleUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildRoleDelete(handler: suspend DispatchBase.(GuildRoleDelete) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override suspend fun onGuildInviteCreate(handler: suspend DispatchBase.(GuildInviteCreate) -> Unit) {
        intents += GatewayIntent.GUILD_INVITES
    }

    override suspend fun onGuildInviteDelete(handler: suspend DispatchBase.(GuildInviteDelete) -> Unit) {
        intents += GatewayIntent.GUILD_INVITES
    }

    override suspend fun onMessageCreate(handler: suspend DispatchBase.(Message) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override suspend fun onMessageUpdate(handler: suspend DispatchBase.(Message) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override suspend fun onMessageDelete(handler: suspend DispatchBase.(MessageDelete) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override suspend fun onMessageDeleteBulk(handler: suspend DispatchBase.(BulkMessageDelete) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
    }

    override suspend fun onMessageReactionAdd(handler: suspend DispatchBase.(MessageReactionAdd) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override suspend fun onMessageReactionRemove(handler: suspend DispatchBase.(MessageReactionRemove) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override suspend fun onMessageReactionRemoveAll(handler: suspend DispatchBase.(MessageReactionRemoveAll) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override suspend fun onMessageReactionRemoveEmoji(handler: suspend DispatchBase.(MessageReactionRemoveEmoji) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override suspend fun onPresenceUpdate(handler: suspend DispatchBase.(PresenceUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_PRESENCES
    }

    override suspend fun onTypingStart(handler: suspend DispatchBase.(TypingStart) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_TYPING
        intents += GatewayIntent.DIRECT_MESSAGE_TYPING
    }

    override suspend fun onUserUpdate(handler: suspend DispatchBase.(User) -> Unit) {}

    override suspend fun onVoiceStateUpdate(handler: suspend DispatchBase.(VoiceState) -> Unit) {
        intents += GatewayIntent.GUILD_VOICE_STATES
    }

    override suspend fun onVoiceServerUpdate(handler: suspend DispatchBase.(VoiceServerUpdate) -> Unit) {}

    override suspend fun onWebhookUpdate(handler: suspend DispatchBase.(WebhookUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_WEBHOOKS
    }

    override fun <C> forType(): EventDispatcher<C> {
        throw NotImplementedError("forType intentionally not implemented for this class")
    }

    override suspend fun await(): List<Unit> = emptyList()
}
