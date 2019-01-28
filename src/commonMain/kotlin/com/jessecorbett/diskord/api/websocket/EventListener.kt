package com.jessecorbett.diskord.api.websocket

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.events.*

/**
 * The base listener that [DiscordWebSocket] uses to dispatch events to the library consumer.
 */
abstract class EventListener {

    /**
     * Fired on any event.
     *
     * @param event The event type.
     * @param json The json data associated with the event.
     */
    open suspend fun onEvent(event: DiscordEvent, data: Map<String, Any?>) {}

    /**
     * Fired when the gateway acknowledges the connection as ready.
     *
     * @param ready Some bootstrapping information about the current user.
     */
    open suspend fun onReady(ready: Ready) {}

    /**
     * Fired when the gateway acknowledges the connection has resumed.
     *
     * @param resumed Resume trace info (not generally useful).
     */
    open suspend fun onResumed(resumed: Resumed) {}

    /**
     * Fired when a channel is created, the current user gets access to a channel, or the current user receives a DM.
     *
     * @param channel The created/received channel.
     */
    open suspend fun onChannelCreate(channel: Channel) {}

    /**
     * Fired when the a channel is updated.
     *
     * @param channel The updated channel.
     */
    open suspend fun onChannelUpdate(channel: Channel) {}

    /**
     * Fired when a channel is deleted.
     *
     * @param channel The deleted channel.
     */
    open suspend fun onChannelDelete(channel: Channel) {}

    /**
     * Fired when the pins in a channel are updated.
     *
     * @param channelPinUpdate The updated pin information.
     */
    open suspend fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate) {}

    /**
     * Fired when a guild is created, the current user is added to a guild, the guild became available, or just after [onReady] to lazy load guilds the user is in.
     *
     * @param guild The guild created, joined, or lazy loaded.
     */
    open suspend fun onGuildCreate(guild: CreatedGuild) {}

    /**
     * Fired when a guild is updated.
     *
     * @param guild The updated guild.
     */
    open suspend fun onGuildUpdate(guild: Guild) {}

    /**
     * Fired when a guild is deleted, made unavailable, or the user left or was removed from the guild.
     *
     * @param guild The deleted channel.
     */
    open suspend fun onGuildDelete(guild: Guild) {}


    /**
     * Fired when a user was banned from a guild.
     *
     * @param ban The ban created.
     */
    open suspend fun onGuildBanAdd(ban: GuildBan) {}

    /**
     * Fired when a user was unbanned from a guild.
     *
     * @param ban The ban removed.
     */
    open suspend fun onGuildBanRemove(ban: GuildBan) {}

    /**
     * Fired when a guild has changed their set of custom emoji.
     *
     * @param emojiUpdate The updated emoji set.
     */
    open suspend fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) {}

    /**
     * Fired when a guild has updated their integrations.
     *
     * @param integrationUpdate The updated guild.
     */
    open suspend fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) {}

    /**
     * Fired when a user joins a guild.
     *
     * @param guildMember The new guild member.
     */
    open suspend fun onGuildMemberAdd(guildMember: GuildMemberAdd) {}

    /**
     * Fired when a guild member is updated, such as changing nickname.
     *
     * @param guildMemberUpdate The updates to the guild member.
     */
    open suspend fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) {}

    /**
     * Fired when a user leaves a guild.
     *
     * @param guildMemberRemove The user that left the guild.
     */
    open suspend fun onGuildMemberRemove(guildMemberRemove: GuildMemberRemove) {}

    /**
     * Fired when the gateway lazy loads chunks of guild members.
     *
     * @param guildMembers Some or all of the guild members being lazy loaded.
     */
    open suspend fun onGuildMemberChunk(guildMembers: GuildMembersChunk) {}

    /**
     * Fired when a role has been created.
     *
     * @param guildRoleCreate The created role.
     */
    open suspend fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) {}

    /**
     * Fired when a role has been updated.
     *
     * @param guildRoleUpdate The updated role.
     */
    open suspend fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) {}

    /**
     * Fired when a role has been deleted.
     *
     * @param guildRoleDelete The deleted role.
     */
    open suspend fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) {}

    /**
     * Fired when a message has been created.
     *
     * @param message The created message.
     */
    open suspend fun onMessageCreate(message: Message) {}

    /**
     * Fired when a message has been updated.
     *
     * @param message The updated message.
     */
    open suspend fun onMessageUpdate(message: MessageUpdate) {}

    /**
     * Fired when a message has been deleted.
     *
     * @param message The deleted message.
     */
    open suspend fun onMessageDelete(message: MessageDelete) {}

    /**
     * Fired when there has been a bulk message delete.
     *
     * @param message The created message.
     */
    open suspend fun onMessageBulkDelete(message: BulkMessageDelete) {}

    /**
     * Fired when a reaction was added to a message.
     *
     * @param reactionAdd The reaction added.
     */
    open suspend fun onMessageReactionAdd(reactionAdd: MessageReaction) {}

    /**
     * Fired when a reaction was remove from a message.
     *
     * @param reactionRemove The reaction removed.
     */
    open suspend fun onMessageReactionRemove(reactionRemove: MessageReaction) {}

    /**
     * Fired when all reactions are removed from a message.
     *
     * @param messageReactionRemoveAll message the reactions were removed from.
     */
    open suspend fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) {}

    /**
     * Fired when a user's presence has updated.
     *
     * @param presenceUpdate The updated presence.
     */
    open suspend fun onPresenceUpdate(presenceUpdate: PresenceUpdate) {}

    /**
     * Fired when a user has started typing.
     *
     * @param typingStart The information about the user typing.
     */
    open suspend fun onTypingStart(typingStart: TypingStart) {}

    /**
     * Fired when a user has been updated.
     *
     * @param user The updated User.
     */
    open suspend fun onUserUpdate(user: User) {}

    /**
     * Fired when a user's voice state has updated.
     *
     * @param voiceState The updated voice state.
     */
    open suspend fun onVoiceStateUpdate(voiceState: VoiceState) {}

    /**
     * Fired when a guild's voice server has updated.
     *
     * @param voiceServerUpdate The updated voice server information.
     */
    open suspend fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) {}

    /**
     * Fired when a webhook is updated.
     *
     * @param webhookUpdate The channel with the updated webhook.
     */
    open suspend fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) {}
}
