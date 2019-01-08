package com.jessecorbett.diskord.api.websocket

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.jessecorbett.diskord.api.websocket.events.*
import com.jessecorbett.diskord.internal.jsonMapper

suspend fun dispatchEvent(eventListener: EventListener, event: DiscordEvent, data: JsonNode) {
    eventListener.onEvent(event, data.asText())
    when (event) {
        DiscordEvent.READY -> eventListener.onReady(jsonMapper.treeToValue(data))
        DiscordEvent.RESUMED -> eventListener.onResumed(jsonMapper.treeToValue(data))
        DiscordEvent.CHANNEL_CREATE -> eventListener.onChannelCreate(jsonMapper.treeToValue(data))
        DiscordEvent.CHANNEL_UPDATE -> eventListener.onChannelUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.CHANNEL_DELETE -> eventListener.onChannelDelete(jsonMapper.treeToValue(data))
        DiscordEvent.CHANNEL_PINS_UPDATE -> eventListener.onChannelPinsUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_CREATE -> eventListener.onGuildCreate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_UPDATE -> eventListener.onGuildUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_DELETE -> eventListener.onGuildDelete(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_BAN_ADD -> eventListener.onGuildBanAdd(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_BAN_REMOVE -> eventListener.onGuildBanRemove(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_EMOJIS_UPDATE -> eventListener.onGuildEmojiUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> eventListener.onGuildIntegrationsUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_MEMBER_ADD -> eventListener.onGuildMemberAdd(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_MEMBER_UPDATE -> eventListener.onGuildMemberUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_MEMBER_DELETE -> eventListener.onGuildMemberRemove(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_MEMBERS_CHUNK -> eventListener.onGuildMemberChunk(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_ROLE_CREATE -> eventListener.onGuildRoleCreate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_ROLE_UPDATE -> eventListener.onGuildRoleUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.GUILD_ROLE_DELETE -> eventListener.onGuildRoleDelete(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_CREATE -> eventListener.onMessageCreate(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_UPDATE -> eventListener.onMessageUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_DELETE -> eventListener.onMessageDelete(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_DELETE_BULK -> eventListener.onMessageBulkDelete(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_REACTION_ADD -> eventListener.onMessageReactionAdd(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_REACTION_REMOVE -> eventListener.onMessageReactionRemove(jsonMapper.treeToValue(data))
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> eventListener.onMessageReactionRemoveAll(jsonMapper.treeToValue(data))
        DiscordEvent.PRESENCE_UPDATE -> eventListener.onPresenceUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.TYPING_START -> eventListener.onTypingStart(jsonMapper.treeToValue(data))
        DiscordEvent.USER_UPDATE -> eventListener.onUserUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.VOICE_STATE_UPDATE -> eventListener.onVoiceStateUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.VOICE_SERVER_UPDATE -> eventListener.onVoiceServerUpdate(jsonMapper.treeToValue(data))
        DiscordEvent.WEBHOOKS_UPDATE -> eventListener.onWebhooksUpdate(jsonMapper.treeToValue(data))
    }
}
