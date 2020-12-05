package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.serialization.json.JsonElement

/**
 * Maps events from [DiscordEvent] to corresponding [EventListener] method implementations.
 *
 * @param eventListener The event listener to map events to.
 * @param event The discord event being mapped.
 * @param data The event data to be deserialized.
 */
public suspend fun dispatchEvent(eventListener: EventListener, event: DiscordEvent, data: JsonElement) {
    eventListener.onEvent(event, data)
    when (event) {
        DiscordEvent.READY -> eventListener.onReady(defaultJson.decodeFromJsonElement(Ready.serializer(), data))
        DiscordEvent.RESUMED -> eventListener.onResumed(defaultJson.decodeFromJsonElement(Resumed.serializer(), data))
        DiscordEvent.CHANNEL_CREATE -> eventListener.onChannelCreate(
            defaultJson.decodeFromJsonElement(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_UPDATE -> eventListener.onChannelUpdate(
            defaultJson.decodeFromJsonElement(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_DELETE -> eventListener.onChannelDelete(
            defaultJson.decodeFromJsonElement(
                Channel.serializer(),
                data
            )
        )
        DiscordEvent.CHANNEL_PINS_UPDATE -> eventListener.onChannelPinsUpdate(
            defaultJson.decodeFromJsonElement(
                ChannelPinUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_CREATE -> eventListener.onGuildCreate(
            defaultJson.decodeFromJsonElement(
                CreatedGuild.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_UPDATE -> eventListener.onGuildUpdate(defaultJson.decodeFromJsonElement(Guild.serializer(), data))
        DiscordEvent.GUILD_DELETE -> eventListener.onGuildDelete(defaultJson.decodeFromJsonElement(Guild.serializer(), data))
        DiscordEvent.GUILD_BAN_ADD -> eventListener.onGuildBanAdd(defaultJson.decodeFromJsonElement(GuildBan.serializer(), data))
        DiscordEvent.GUILD_BAN_REMOVE -> eventListener.onGuildBanRemove(
            defaultJson.decodeFromJsonElement(
                GuildBan.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_EMOJIS_UPDATE -> eventListener.onGuildEmojiUpdate(
            defaultJson.decodeFromJsonElement(
                GuildEmojiUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> eventListener.onGuildIntegrationsUpdate(
            defaultJson.decodeFromJsonElement(
                GuildIntegrationUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_ADD -> eventListener.onGuildMemberAdd(
            defaultJson.decodeFromJsonElement(
                GuildMemberAdd.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_UPDATE -> eventListener.onGuildMemberUpdate(
            defaultJson.decodeFromJsonElement(
                GuildMemberUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBER_REMOVE -> eventListener.onGuildMemberRemove(
            defaultJson.decodeFromJsonElement(
                GuildMemberRemove.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_MEMBERS_CHUNK -> eventListener.onGuildMemberChunk(
            defaultJson.decodeFromJsonElement(
                GuildMembersChunk.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_CREATE -> eventListener.onGuildRoleCreate(
            defaultJson.decodeFromJsonElement(
                GuildRoleCreate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_UPDATE -> eventListener.onGuildRoleUpdate(
            defaultJson.decodeFromJsonElement(
                GuildRoleUpdate.serializer(),
                data
            )
        )
        DiscordEvent.GUILD_ROLE_DELETE -> eventListener.onGuildRoleDelete(
            defaultJson.decodeFromJsonElement(
                GuildRoleDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_CREATE -> eventListener.onMessageCreate(
            defaultJson.decodeFromJsonElement(
                Message.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_UPDATE -> eventListener.onMessageUpdate(
            defaultJson.decodeFromJsonElement(
                MessageUpdate.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_DELETE -> eventListener.onMessageDelete(
            defaultJson.decodeFromJsonElement(
                MessageDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_DELETE_BULK -> eventListener.onMessageBulkDelete(
            defaultJson.decodeFromJsonElement(
                BulkMessageDelete.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_ADD -> eventListener.onMessageReactionAdd(
            defaultJson.decodeFromJsonElement(
                MessageReaction.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_REMOVE -> eventListener.onMessageReactionRemove(
            defaultJson.decodeFromJsonElement(
                MessageReaction.serializer(),
                data
            )
        )
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> eventListener.onMessageReactionRemoveAll(
            defaultJson.decodeFromJsonElement(
                MessageReactionRemoveAll.serializer(),
                data
            )
        )
        DiscordEvent.PRESENCE_UPDATE -> eventListener.onPresenceUpdate(
            defaultJson.decodeFromJsonElement(
                PresenceUpdate.serializer(),
                data
            )
        )
        DiscordEvent.TYPING_START -> eventListener.onTypingStart(
            defaultJson.decodeFromJsonElement(
                TypingStart.serializer(),
                data
            )
        )
        DiscordEvent.USER_UPDATE -> eventListener.onUserUpdate(defaultJson.decodeFromJsonElement(User.serializer(), data))
        DiscordEvent.VOICE_STATE_UPDATE -> eventListener.onVoiceStateUpdate(
            defaultJson.decodeFromJsonElement(
                VoiceState.serializer(),
                data
            )
        )
        DiscordEvent.VOICE_SERVER_UPDATE -> eventListener.onVoiceServerUpdate(
            defaultJson.decodeFromJsonElement(
                VoiceServerUpdate.serializer(),
                data
            )
        )
        DiscordEvent.WEBHOOKS_UPDATE -> eventListener.onWebhooksUpdate(
            defaultJson.decodeFromJsonElement(
                WebhookUpdate.serializer(),
                data
            )
        )
    }
}
