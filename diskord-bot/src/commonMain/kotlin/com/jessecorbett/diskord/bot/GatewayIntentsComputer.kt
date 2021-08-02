package com.jessecorbett.diskord.bot

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.EventDispatcher
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import kotlinx.serialization.json.JsonElement

internal class GatewayIntentsComputer : EventDispatcher<Unit> {
    val intents: MutableList<GatewayIntent> = mutableListOf()

    override fun onReady(handler: suspend (Ready) -> Unit) {}

    override fun onResume(handler: suspend (Resumed) -> Unit) {}

    override fun onChannelCreate(handler: suspend (Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onChannelUpdate(handler: suspend (Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onChannelDelete(handler: suspend (Channel) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onChannelPinsUpdate(handler: suspend (ChannelPinUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override fun onGuildCreate(handler: suspend (Guild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildUpdate(handler: suspend (Guild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildDelete(handler: suspend (UnavailableGuild) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildBanAdd(handler: suspend (GuildBan) -> Unit) {
        intents += GatewayIntent.GUILD_BANS
    }

    override fun onGuildBanRemove(handler: suspend (GuildBan) -> Unit) {
        intents += GatewayIntent.GUILD_BANS
    }

    override fun onGuildEmojiUpdate(handler: suspend (GuildEmojiUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_EMOJI_AND_STICKERS
    }

    override fun onGuildStickersUpdate(handler: suspend (GuildStickersUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_EMOJI_AND_STICKERS
    }

    override fun onGuildIntegrationsUpdate(handler: suspend (GuildIntegrationUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_INTEGRATIONS
    }

    override fun onGuildMemberAdd(handler: suspend (GuildMemberAdd) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override fun onGuildMemberUpdate(handler: suspend (GuildMemberUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override fun onGuildMemberRemove(handler: suspend (GuildMemberRemove) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS
    }

    override fun onGuildMembersChunk(handler: suspend (GuildMembersChunk) -> Unit) {
        intents += GatewayIntent.GUILD_MEMBERS // Not technically necessary, but safe to assume by default
    }

    override fun onGuildRoleCreate(handler: suspend (GuildRoleCreate) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildRoleUpdate(handler: suspend (GuildRoleUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildRoleDelete(handler: suspend (GuildRoleDelete) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onGuildInviteCreate(handler: suspend (GuildInviteCreate) -> Unit) {
        intents += GatewayIntent.GUILD_INVITES
    }

    override fun onGuildInviteDelete(handler: suspend (GuildInviteDelete) -> Unit) {
        intents += GatewayIntent.GUILD_INVITES
    }

    override fun onMessageCreate(handler: suspend (Message) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override fun onMessageUpdate(handler: suspend (Message) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override fun onMessageDelete(handler: suspend (MessageDelete) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
        intents += GatewayIntent.DIRECT_MESSAGES
    }

    override fun onMessageDeleteBulk(handler: suspend (BulkMessageDelete) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGES
    }

    override fun onMessageReactionAdd(handler: suspend (MessageReactionAdd) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override fun onMessageReactionRemove(handler: suspend (MessageReactionRemove) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override fun onMessageReactionRemoveAll(handler: suspend (MessageReactionRemoveAll) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override fun onMessageReactionRemoveEmoji(handler: suspend (MessageReactionRemoveEmoji) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_REACTIONS
        intents += GatewayIntent.DIRECT_MESSAGE_REACTIONS
    }

    override fun onThreadCreate(handler: suspend (GuildThread) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadUpdate(handler: suspend (GuildThread) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadDelete(handler: suspend (ThreadDelete) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadListSync(handler: suspend (ThreadListSync) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadMemberUpdate(handler: suspend (ThreadMember) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadMembersUpdate(handler: suspend (ThreadMembersUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
    }

    override fun onThreadMembersUpdatePrivileged(handler: suspend (ThreadMembersUpdate) -> Unit) {
        intents += GatewayIntent.GUILDS
        intents += GatewayIntent.GUILD_MESSAGES
    }

    override fun onPresenceUpdate(handler: suspend (PresenceUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_PRESENCES
    }

    override fun onTypingStart(handler: suspend (TypingStart) -> Unit) {
        intents += GatewayIntent.GUILD_MESSAGE_TYPING
        intents += GatewayIntent.DIRECT_MESSAGE_TYPING
    }

    override fun onUserUpdate(handler: suspend (User) -> Unit) {}

    override fun onVoiceStateUpdate(handler: suspend (VoiceState) -> Unit) {
        intents += GatewayIntent.GUILD_VOICE_STATES
    }

    override fun onVoiceServerUpdate(handler: suspend (VoiceServerUpdate) -> Unit) {}

    override fun onWebhookUpdate(handler: suspend (WebhookUpdate) -> Unit) {
        intents += GatewayIntent.GUILD_WEBHOOKS
    }

    override fun <C> forType(): EventDispatcher<C> {
        throw NotImplementedError("forType intentionally not implemented for this class")
    }

    override suspend fun handleEvent(event: DiscordEvent, json: JsonElement): List<Unit> = emptyList()
}
