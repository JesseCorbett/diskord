package com.jessecorbett.diskord.internal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.jessecorbett.diskord.EventListener
import com.jessecorbett.diskord.api.Channel
import com.jessecorbett.diskord.api.Guild
import com.jessecorbett.diskord.api.Message
import com.jessecorbett.diskord.api.User
import com.jessecorbett.diskord.api.gateway.commands.Resume
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.models.*

fun dispatchEvent(eventListener: EventListener, event: DiscordEvent, data: JsonNode) {
    eventListener.onEvent(event)
    when (event) {
        DiscordEvent.READY -> {
            val readyData = jsonMapper.treeToValue<Ready>(data)
            eventListener.onReady(readyData)
        }
        DiscordEvent.RESUMED -> {
            val resumedData = jsonMapper.treeToValue<Resume>(data)
            eventListener.onResumed(resumedData)
        }
        DiscordEvent.CHANNEL_CREATE -> {
            val channel = jsonMapper.treeToValue<Channel>(data)
            eventListener.onChannelCreate(channel)
        }
        DiscordEvent.CHANNEL_UPDATE -> {
            val channel = jsonMapper.treeToValue<Channel>(data)
            eventListener.onChannelUpdate(channel)
        }
        DiscordEvent.CHANNEL_DELETE -> {
            val channel = jsonMapper.treeToValue<Channel>(data)
            eventListener.onChannelDelete(channel)
        }
        DiscordEvent.CHANNEL_PINS_UPDATE -> {
            val pinUpdate = jsonMapper.treeToValue<ChannelPinUpdate>(data)
            eventListener.onChannelPinsUpdate(pinUpdate)
        }
        DiscordEvent.GUILD_CREATE -> {
            val guildCreateData = jsonMapper.treeToValue<CreatedGuild>(data)
            eventListener.onGuildCreate(guildCreateData)
        }
        DiscordEvent.GUILD_UPDATE -> {
            val guild = jsonMapper.treeToValue<Guild>(data)
            eventListener.onGuildUpdate(guild)
        }
        DiscordEvent.GUILD_DELETE -> {
            val guild = jsonMapper.treeToValue<Guild>(data)
            eventListener.onGuildDelete(guild)
        }
        DiscordEvent.GUILD_BAN_ADD -> {
            val ban = jsonMapper.treeToValue<GuildBan>(data)
            eventListener.onGuildBanAdd(ban)
        }
        DiscordEvent.GUILD_BAN_REMOVE -> {
            val ban = jsonMapper.treeToValue<GuildBan>(data)
            eventListener.onGuildBanRemove(ban)
        }
        DiscordEvent.GUILD_EMOJIS_UPDATE -> {
            val emojiUpdate = jsonMapper.treeToValue<GuildEmojiUpdate>(data)
            eventListener.onGuildEmojiUpdate(emojiUpdate)
        }
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> {
            val integrationUpdate = jsonMapper.treeToValue<GuildIntegrationUpdate>(data)
            eventListener.onGuildIntegrationsUpdate(integrationUpdate)
        }
        DiscordEvent.GUILD_MEMBER_ADD -> {
            val member = jsonMapper.treeToValue<GuildMemberAdd>(data)
            eventListener.onGuildMemberAdd(member)
        }
        DiscordEvent.GUILD_MEMBER_UPDATE -> {
            val member = jsonMapper.treeToValue<GuildMemberUpdate>(data)
            eventListener.onGuildMemberUpdate(member)
        }
        DiscordEvent.GUILD_MEMBER_DELETE -> {
            val member = jsonMapper.treeToValue<GuildMemeberRemove>(data)
            eventListener.onGuildMemberRemove(member)
        }
        DiscordEvent.GUILD_MEMBERS_CHUNK -> {
            val members = jsonMapper.treeToValue<GuildMembersChunk>(data)
            eventListener.onGuildMemberChunk(members)
        }
        DiscordEvent.GUILD_ROLE_CREATE -> {
            val role = jsonMapper.treeToValue<GuildRoleCreate>(data)
            eventListener.onGuildRoleCreate(role)
        }
        DiscordEvent.GUILD_ROLE_UPDATE -> {
            val role = jsonMapper.treeToValue<GuildRoleUpdate>(data)
            eventListener.onGuildRoleUpdate(role)
        }
        DiscordEvent.GUILD_ROLE_DELETE -> {
            val role = jsonMapper.treeToValue<GuildRoleDelete>(data)
            eventListener.onGuildRoleDelete(role)
        }
        DiscordEvent.MESSAGE_CREATE -> {
            val message = jsonMapper.treeToValue<Message>(data)
            eventListener.onMessageCreate(message)
        }
        DiscordEvent.MESSAGE_UPDATE -> {
            val message = jsonMapper.treeToValue<Message>(data)
            eventListener.onMessageUpdate(message)
        }
        DiscordEvent.MESSAGE_DELETE -> {
            val message = jsonMapper.treeToValue<MessageDelete>(data)
            eventListener.onMessageDelete(message)
        }
        DiscordEvent.MESSAGE_DELETE_BULK -> {
            val messages = jsonMapper.treeToValue<BulkMessageDelete>(data)
            eventListener.onMessageBulkDelete(messages)
        }
        DiscordEvent.MESSAGE_REACTION_ADD -> {
            val reaction = jsonMapper.treeToValue<MessageReaction>(data)
            eventListener.onMessageReactionAdd(reaction)
        }
        DiscordEvent.MESSAGE_REACTION_REMOVE -> {
            val reaction = jsonMapper.treeToValue<MessageReaction>(data)
            eventListener.onMessageReactionRemove(reaction)
        }
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> {
            val removeReactions = jsonMapper.treeToValue<MessageReactionRemoveAll>(data)
            eventListener.onMessageReactionRemoveAll(removeReactions)
        }
        DiscordEvent.PRESENCE_UPDATE -> {
            val presenceUpdate = jsonMapper.treeToValue<PresenceUpdate>(data)
            eventListener.onPresenceUpdate(presenceUpdate)
        }
        DiscordEvent.TYPING_START -> {
            val typingStart = jsonMapper.treeToValue<TypingStart>(data)
            eventListener.onTypingStart(typingStart)
        }
        DiscordEvent.USER_UPDATE -> {
            val user = jsonMapper.treeToValue<User>(data)
            eventListener.onUserUpdate(user)
        }
        DiscordEvent.VOICE_STATE_UPDATE -> {
            val voiceState = jsonMapper.treeToValue<VoiceState>(data)
            eventListener.onVoiceStateUpdate(voiceState)
        }
        DiscordEvent.VOICE_SERVER_UPDATE -> {
            val voiceServerUpdate = jsonMapper.treeToValue<VoiceServerUpdate>(data)
            eventListener.onVoiceServerUpdate(voiceServerUpdate)
        }
        DiscordEvent.WEBHOOKS_UPDATE -> {
            val webhooksUpdate = jsonMapper.treeToValue<WebhookUpdate>(data)
            eventListener.onWebhooksUpdate(webhooksUpdate)
        }
    }
}
