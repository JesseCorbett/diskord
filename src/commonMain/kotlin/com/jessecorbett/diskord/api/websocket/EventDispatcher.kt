package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.events.*
import kotlinx.serialization.Mapper

/**
 * Maps events from [DiscordEvent] to corresponding [EventListener] method implementations.
 *
 * @param eventListener The event listener to map events to.
 * @param event The discord event being mapped.
 * @param data The event data to be deserialized.
 */
suspend fun dispatchEvent(eventListener: EventListener, event: DiscordEvent, data: Map<String, Any?>) {
    eventListener.onEvent(event, data)
    when (event) {
        DiscordEvent.READY -> eventListener.onReady(Mapper.unmapNullable(Ready.serializer(), data))
        DiscordEvent.RESUMED -> eventListener.onResumed(Mapper.unmapNullable(Resumed.serializer(), data))
        DiscordEvent.CHANNEL_CREATE -> eventListener.onChannelCreate(Mapper.unmapNullable(Channel.serializer(), data))
        DiscordEvent.CHANNEL_UPDATE -> eventListener.onChannelUpdate(Mapper.unmapNullable(Channel.serializer(), data))
        DiscordEvent.CHANNEL_DELETE -> eventListener.onChannelDelete(Mapper.unmapNullable(Channel.serializer(), data))
        DiscordEvent.CHANNEL_PINS_UPDATE -> eventListener.onChannelPinsUpdate(Mapper.unmapNullable(ChannelPinUpdate.serializer(), data))
        DiscordEvent.GUILD_CREATE -> eventListener.onGuildCreate(Mapper.unmapNullable(CreatedGuild.serializer(), data))
        DiscordEvent.GUILD_UPDATE -> eventListener.onGuildUpdate(Mapper.unmapNullable(Guild.serializer(), data))
        DiscordEvent.GUILD_DELETE -> eventListener.onGuildDelete(Mapper.unmapNullable(Guild.serializer(), data))
        DiscordEvent.GUILD_BAN_ADD -> eventListener.onGuildBanAdd(Mapper.unmapNullable(GuildBan.serializer(), data))
        DiscordEvent.GUILD_BAN_REMOVE -> eventListener.onGuildBanRemove(Mapper.unmapNullable(GuildBan.serializer(), data))
        DiscordEvent.GUILD_EMOJIS_UPDATE -> eventListener.onGuildEmojiUpdate(Mapper.unmapNullable(GuildEmojiUpdate.serializer(), data))
        DiscordEvent.GUILD_INTEGRATIONS_UPDATE -> eventListener.onGuildIntegrationsUpdate(Mapper.unmapNullable(GuildIntegrationUpdate.serializer(), data))
        DiscordEvent.GUILD_MEMBER_ADD -> eventListener.onGuildMemberAdd(Mapper.unmapNullable(GuildMemberAdd.serializer(), data))
        DiscordEvent.GUILD_MEMBER_UPDATE -> eventListener.onGuildMemberUpdate(Mapper.unmapNullable(GuildMemberUpdate.serializer(), data))
        DiscordEvent.GUILD_MEMBER_DELETE -> eventListener.onGuildMemberRemove(Mapper.unmapNullable(GuildMemberRemove.serializer(), data))
        DiscordEvent.GUILD_MEMBERS_CHUNK -> eventListener.onGuildMemberChunk(Mapper.unmapNullable(GuildMembersChunk.serializer(), data))
        DiscordEvent.GUILD_ROLE_CREATE -> eventListener.onGuildRoleCreate(Mapper.unmapNullable(GuildRoleCreate.serializer(), data))
        DiscordEvent.GUILD_ROLE_UPDATE -> eventListener.onGuildRoleUpdate(Mapper.unmapNullable(GuildRoleUpdate.serializer(), data))
        DiscordEvent.GUILD_ROLE_DELETE -> eventListener.onGuildRoleDelete(Mapper.unmapNullable(GuildRoleDelete.serializer(), data))
        DiscordEvent.MESSAGE_CREATE -> eventListener.onMessageCreate(Mapper.unmapNullable(Message.serializer(), data))
        DiscordEvent.MESSAGE_UPDATE -> eventListener.onMessageUpdate(Mapper.unmapNullable(MessageUpdate.serializer(), data))
        DiscordEvent.MESSAGE_DELETE -> eventListener.onMessageDelete(Mapper.unmapNullable(MessageDelete.serializer(), data))
        DiscordEvent.MESSAGE_DELETE_BULK -> eventListener.onMessageBulkDelete(Mapper.unmapNullable(BulkMessageDelete.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_ADD -> eventListener.onMessageReactionAdd(Mapper.unmapNullable(MessageReaction.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_REMOVE -> eventListener.onMessageReactionRemove(Mapper.unmapNullable(MessageReaction.serializer(), data))
        DiscordEvent.MESSAGE_REACTION_REMOVE_ALL -> eventListener.onMessageReactionRemoveAll(Mapper.unmapNullable(MessageReactionRemoveAll.serializer(), data))
        DiscordEvent.PRESENCE_UPDATE -> eventListener.onPresenceUpdate(Mapper.unmapNullable(PresenceUpdate.serializer(), data))
        DiscordEvent.TYPING_START -> eventListener.onTypingStart(Mapper.unmapNullable(TypingStart.serializer(), data))
        DiscordEvent.USER_UPDATE -> eventListener.onUserUpdate(Mapper.unmapNullable(User.serializer(), data))
        DiscordEvent.VOICE_STATE_UPDATE -> eventListener.onVoiceStateUpdate(Mapper.unmapNullable(VoiceState.serializer(), data))
        DiscordEvent.VOICE_SERVER_UPDATE -> eventListener.onVoiceServerUpdate(Mapper.unmapNullable(VoiceServerUpdate.serializer(), data))
        DiscordEvent.WEBHOOKS_UPDATE -> eventListener.onWebhooksUpdate(Mapper.unmapNullable(WebhookUpdate.serializer(), data))
    }
}
