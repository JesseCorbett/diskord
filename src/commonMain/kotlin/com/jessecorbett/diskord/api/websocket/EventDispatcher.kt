package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.events.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Maps events from [DiscordEvent] to corresponding [EventListener] method implementations.
 *
 * @param eventListener The event listener to map events to.
 * @param event The discord event being mapped.
 * @param data The event data to be deserialized.
 */
suspend fun dispatchEvent(eventListener: EventListener, event: DiscordEvent, data: JsonElement) {
    eventListener.onEvent(event, data)
    when (event) {
        DiscordEvent.READY -> eventListener.onReady(Json.nonstrict.fromJson(Ready.serializer(), data))
        DiscordEvent.RESUMED -> eventListener.onResumed(Json.nonstrict.fromJson(Resumed.serializer(), data))
        DiscordEvent.CHANNEL_CREATE -> eventListener.onChannelCreate(Json.nonstrict.fromJson(Channel.serializer(), data))
        DiscordEvent.CHANNEL_UPDATE -> eventListener.onChannelUpdate(Json.nonstrict.fromJson(Channel.serializer(), data))
        DiscordEvent.CHANNEL_DELETE -> eventListener.onChannelDelete(Json.nonstrict.fromJson(Channel.serializer(), data))
        DiscordEvent.CHANNEL_PINS_UPDATE -> eventListener.onChannelPinsUpdate(Json.nonstrict.fromJson(ChannelPinUpdate.serializer(), data))
        DiscordEvent.GUILD_CREATE -> eventListener.onGuildCreate(Json.nonstrict.fromJson(CreatedGuild.serializer(), data))
        DiscordEvent.GUILD_UPDATE -> eventListener.onGuildUpdate(Json.nonstrict.fromJson(Guild.serializer(), data))
        DiscordEvent.GUILD_DELETE -> eventListener.onGuildDelete(Json.nonstrict.fromJson(Guild.serializer(), data))
        DiscordEvent.GUILD_BAN_ADD -> eventListener.onGuildBanAdd(Json.nonstrict.fromJson(GuildBan.serializer(), data))
        DiscordEvent.GUILD_BAN_REMOVE -> eventListener.onGuildBanRemove(Json.nonstrict.fromJson(GuildBan.serializer(), data))
        DiscordEvent.GUILD_EMOJIS_UPDATE -> eventListener.onGuildEmojiUpdate(Json.nonstrict.fromJson(GuildEmojiUpdate.serializer(), data))
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> eventListener.onGuildIntegrationsUpdate(Json.nonstrict.fromJson(GuildIntegrationUpdate.serializer(), data))
        DiscordEvent.GUILD_MEMBER_ADD -> eventListener.onGuildMemberAdd(Json.nonstrict.fromJson(GuildMemberAdd.serializer(), data))
        DiscordEvent.GUILD_MEMBER_UPDATE -> eventListener.onGuildMemberUpdate(Json.nonstrict.fromJson(GuildMemberUpdate.serializer(), data))
        DiscordEvent.GUILD_MEMBER_DELETE -> eventListener.onGuildMemberRemove(Json.nonstrict.fromJson(GuildMemberRemove.serializer(), data))
        DiscordEvent.GUILD_MEMBERS_CHUNK -> eventListener.onGuildMemberChunk(Json.nonstrict.fromJson(GuildMembersChunk.serializer(), data))
        DiscordEvent.GUILD_ROLE_CREATE -> eventListener.onGuildRoleCreate(Json.nonstrict.fromJson(GuildRoleCreate.serializer(), data))
        DiscordEvent.GUILD_ROLE_UPDATE -> eventListener.onGuildRoleUpdate(Json.nonstrict.fromJson(GuildRoleUpdate.serializer(), data))
        DiscordEvent.GUILD_ROLE_DELETE -> eventListener.onGuildRoleDelete(Json.nonstrict.fromJson(GuildRoleDelete.serializer(), data))
        DiscordEvent.MESSAGE_CREATE -> eventListener.onMessageCreate(Json.nonstrict.fromJson(Message.serializer(), data))
        DiscordEvent.MESSAGE_UPDATE -> eventListener.onMessageUpdate(Json.nonstrict.fromJson(MessageUpdate.serializer(), data))
        DiscordEvent.MESSAGE_DELETE -> eventListener.onMessageDelete(Json.nonstrict.fromJson(MessageDelete.serializer(), data))
        DiscordEvent.MESSAGE_DELETE_BULK -> eventListener.onMessageBulkDelete(Json.nonstrict.fromJson(BulkMessageDelete.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_ADD -> eventListener.onMessageReactionAdd(Json.nonstrict.fromJson(MessageReaction.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_REMOVE -> eventListener.onMessageReactionRemove(Json.nonstrict.fromJson(MessageReaction.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> eventListener.onMessageReactionRemoveAll(Json.nonstrict.fromJson(MessageReactionRemoveAll.serializer(), data))
        DiscordEvent.PRESENCE_UPDATE -> eventListener.onPresenceUpdate(Json.nonstrict.fromJson(PresenceUpdate.serializer(), data))
        DiscordEvent.TYPING_START -> eventListener.onTypingStart(Json.nonstrict.fromJson(TypingStart.serializer(), data))
        DiscordEvent.USER_UPDATE -> eventListener.onUserUpdate(Json.nonstrict.fromJson(User.serializer(), data))
        DiscordEvent.VOICE_STATE_UPDATE -> eventListener.onVoiceStateUpdate(Json.nonstrict.fromJson(VoiceState.serializer(), data))
        DiscordEvent.VOICE_SERVER_UPDATE -> eventListener.onVoiceServerUpdate(Json.nonstrict.fromJson(VoiceServerUpdate.serializer(), data))
        DiscordEvent.WEBHOOKS_UPDATE -> eventListener.onWebhooksUpdate(Json.nonstrict.fromJson(WebhookUpdate.serializer(), data))
    }
}
