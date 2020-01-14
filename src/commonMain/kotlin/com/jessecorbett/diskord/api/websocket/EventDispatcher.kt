package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.events.*
import com.jessecorbett.diskord.util.defaultJson
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
        DiscordEvent.READY -> eventListener.onReady(defaultJson.fromJson(Ready.serializer(), data))
        DiscordEvent.RESUMED -> eventListener.onResumed(defaultJson.fromJson(Resumed.serializer(), data))
        DiscordEvent.CHANNEL_CREATE -> eventListener.onChannelCreate(
            defaultJson.fromJson(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_UPDATE -> eventListener.onChannelUpdate(
            defaultJson.fromJson(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_DELETE -> eventListener.onChannelDelete(
            defaultJson.fromJson(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_PINS_UPDATE -> eventListener.onChannelPinsUpdate(
            defaultJson.fromJson(
                ChannelPinUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_CREATE -> eventListener.onGuildCreate(
            defaultJson.fromJson(
                CreatedGuild.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_UPDATE -> eventListener.onGuildUpdate(defaultJson.fromJson(Guild.serializer(), data))
        DiscordEvent.GUILD_DELETE -> eventListener.onGuildDelete(defaultJson.fromJson(Guild.serializer(), data))
        DiscordEvent.GUILD_BAN_ADD -> eventListener.onGuildBanAdd(defaultJson.fromJson(GuildBan.serializer(), data))
        DiscordEvent.GUILD_BAN_REMOVE -> eventListener.onGuildBanRemove(
            defaultJson.fromJson(
                GuildBan.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_EMOJIS_UPDATE -> eventListener.onGuildEmojiUpdate(
            defaultJson.fromJson(
                GuildEmojiUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> eventListener.onGuildIntegrationsUpdate(
            defaultJson.fromJson(
                GuildIntegrationUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_ADD -> eventListener.onGuildMemberAdd(
            defaultJson.fromJson(
                GuildMemberAdd.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_UPDATE -> eventListener.onGuildMemberUpdate(
            defaultJson.fromJson(
                GuildMemberUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_REMOVE -> eventListener.onGuildMemberRemove(
            defaultJson.fromJson(
                GuildMemberRemove.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBERS_CHUNK -> eventListener.onGuildMemberChunk(
            defaultJson.fromJson(
                GuildMembersChunk.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_CREATE -> eventListener.onGuildRoleCreate(
            defaultJson.fromJson(
                GuildRoleCreate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_UPDATE -> eventListener.onGuildRoleUpdate(
            defaultJson.fromJson(
                GuildRoleUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_DELETE -> eventListener.onGuildRoleDelete(
            defaultJson.fromJson(
                GuildRoleDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_CREATE -> eventListener.onMessageCreate(
            defaultJson.fromJson(
                Message.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_UPDATE -> eventListener.onMessageUpdate(
            defaultJson.fromJson(
                MessageUpdate.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_DELETE -> eventListener.onMessageDelete(
            defaultJson.fromJson(
                MessageDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_DELETE_BULK -> eventListener.onMessageBulkDelete(
            defaultJson.fromJson(
                BulkMessageDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_ADD -> eventListener.onMessageReactionAdd(
            defaultJson.fromJson(
                MessageReaction.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_REMOVE -> eventListener.onMessageReactionRemove(
            defaultJson.fromJson(
                MessageReaction.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> eventListener.onMessageReactionRemoveAll(
            defaultJson.fromJson(
                MessageReactionRemoveAll.serializer(),
                data
            )
        )
        DiscordEvent.PRESENCE_UPDATE -> eventListener.onPresenceUpdate(
            defaultJson.fromJson(
                PresenceUpdate.serializer(),
                data
            )
        )
        DiscordEvent.TYPING_START -> eventListener.onTypingStart(
            defaultJson.fromJson(
                TypingStart.serializer(),
                data
            )
        )
        DiscordEvent.USER_UPDATE -> eventListener.onUserUpdate(defaultJson.fromJson(User.serializer(), data))
        DiscordEvent.VOICE_STATE_UPDATE -> eventListener.onVoiceStateUpdate(
            defaultJson.fromJson(
                VoiceState.serializer(),
                data
            )
        )
        DiscordEvent.VOICE_SERVER_UPDATE -> eventListener.onVoiceServerUpdate(
            defaultJson.fromJson(
                VoiceServerUpdate.serializer(),
                data
            )
        )
        DiscordEvent.WEBHOOKS_UPDATE -> eventListener.onWebhooksUpdate(
            defaultJson.fromJson(
                WebhookUpdate.serializer(),
                data
            )
        )
    }
}
