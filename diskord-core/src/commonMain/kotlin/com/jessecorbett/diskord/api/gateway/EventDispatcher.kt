package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import com.jessecorbett.diskord.api.interaction.Interaction
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonElement
import mu.KotlinLogging

/**
 * Dispatcher which distributes events to function hooks
 */
@DiskordDsl
public interface EventDispatcher<T> {
    /**
     * Called when a gateway acknowledges the connection as ready.
     *
     * @param handler Bootstrapping information about the current user.
     */
    @DiskordDsl
    public fun onReady(handler: suspend (Ready) -> T)

    /**
     * Called when a gateway acknowledges the connection has resumed.
     *
     * @param handler Resume trace info (not generally useful).
     */
    @DiskordDsl
    public fun onResume(handler: suspend (Resumed) -> T)

    /**
     * Called when a channel is created, the current user gets access to a channel, or the current user receives a DM.
     *
     * @param handler The created/received channel.
     */
    @DiskordDsl
    public fun onChannelCreate(handler: suspend (Channel) -> T)

    /**
     * Called when a channel is updated. Does not include [TextChannel.lastMessageId] updates.
     *
     * @param handler The updated channel.
     */
    @DiskordDsl
    public fun onChannelUpdate(handler: suspend (Channel) -> T)

    /**
     * Called when a channel is deleted.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelDelete(handler: suspend (Channel) -> T)

    /**
     * Called when a message is pinned or unpinned in a channel.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelPinsUpdate(handler: suspend (ChannelPinUpdate) -> T)

    /**
     * Called when the user first connects to lazy-fill unavailable guilds from the [Ready] event,
     * a guild becomes available again, or the user joins a guild.
     *
     * If the bot does not have the [GatewayIntent.GUILD_PRESENCES] intent or the guild has more
     * than 75k members, members and presences returned in this event will only contain your bot
     * and users in voice channels.
     *
     * @param handler The loaded/available/joined guild.
     */
    @DiskordDsl
    public fun onGuildCreate(handler: suspend (Guild) -> T)

    /**
     * Called when a guild is updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildUpdate(handler: suspend (Guild) -> T)

    /**
     * Called when a guild is unavailable or the user is removed.
     *
     * If [UnavailableGuild.unavailable] is not set, the user was removed from the guild.
     *
     * @param handler The unavailable guild.
     */
    @DiskordDsl
    public fun onGuildDelete(handler: suspend (UnavailableGuild) -> T)

    /**
     * Called when a user is banned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanAdd(handler: suspend (GuildBan) -> T)

    /**
     * Called when a user is unbanned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanRemove(handler: suspend (GuildBan) -> T)

    /**
     * Called when a guild's custom emoji have been updated.
     *
     * @param handler The updated emoji.
     */
    @DiskordDsl
    public fun onGuildEmojiUpdate(handler: suspend (GuildEmojiUpdate) -> T)

    /**
     * Called when a guild's custom stickers have been updated.
     *
     * @param handler The updated sticker.
     */
    @DiskordDsl
    public fun onGuildStickersUpdate(handler: suspend (GuildStickersUpdate) -> T)

    /**
     * Called when a guild's integrations have been updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildIntegrationsUpdate(handler: suspend (GuildIntegrationUpdate) -> T)

    /**
     * Called when someone joins a guild
     *
     * @param handler The new guild member.
     */
    @DiskordDsl
    public fun onGuildMemberAdd(handler: suspend (GuildMemberAdd) -> T)

    /**
     * Called when a guild membership is updated
     *
     * @param handler The guild member update.
     */
    @DiskordDsl
    public fun onGuildMemberUpdate(handler: suspend (GuildMemberUpdate) -> T)

    /**
     * Called when someone is removed from a guild, either from leaving, being kicked, or being banned
     *
     * @param handler The removed guild member.
     */
    @DiskordDsl
    public fun onGuildMemberRemove(handler: suspend (GuildMemberRemove) -> T)

    /**
     * Sent in response to Guild Request Members
     *
     * You can use the chunk_index and chunk_count to calculate how many chunks are left for your request.
     *
     * @param handler A requested chunk of guild members.
     */
    @DiskordDsl
    public fun onGuildMembersChunk(handler: suspend (GuildMembersChunk) -> T)

    /**
     * Called when a role is created
     *
     * @param handler The created role.
     */
    @DiskordDsl
    public fun onGuildRoleCreate(handler: suspend (GuildRoleCreate) -> T)

    /**
     * Called when a role is updated
     *
     * @param handler The updated role.
     */
    @DiskordDsl
    public fun onGuildRoleUpdate(handler: suspend (GuildRoleUpdate) -> T)

    /**
     * Called when a role is deleted
     *
     * @param handler The deleted role.
     */
    @DiskordDsl
    public fun onGuildRoleDelete(handler: suspend (GuildRoleDelete) -> T)

    /**
     * Called when an invite is created
     *
     * @param handler The created invite.
     */
    @DiskordDsl
    public fun onGuildInviteCreate(handler: suspend (GuildInviteCreate) -> T)

    /**
     * Called when an invite is deleted
     *
     * @param handler The deleted invite.
     */
    @DiskordDsl
    public fun onGuildInviteDelete(handler: suspend (GuildInviteDelete) -> T)

    /**
     * Called when a message has been created.
     *
     * @param handler The created message.
     */
    @DiskordDsl
    public fun onMessageCreate(handler: suspend (Message) -> T)

    /**
     * Called when a message has been updated.
     *
     * @param handler The updated message.
     */
    @DiskordDsl
    public fun onMessageUpdate(handler: suspend (Message) -> T)

    /**
     * Called when a message has been deleted.
     *
     * @param handler The deleted message.
     */
    @DiskordDsl
    public fun onMessageDelete(handler: suspend (MessageDelete) -> T)

    /**
     * Called when messages have been bulk deleted.
     *
     * @param handler The deleted messages.
     */
    @DiskordDsl
    public fun onMessageDeleteBulk(handler: suspend (BulkMessageDelete) -> T)

    /**
     * Called when a message is reacted to.
     *
     * @param handler The added reaction.
     */
    @DiskordDsl
    public fun onMessageReactionAdd(handler: suspend (MessageReactionAdd) -> T)

    /**
     * Called when a message reaction is removed.
     *
     * @param handler The removed reaction.
     */
    @DiskordDsl
    public fun onMessageReactionRemove(handler: suspend (MessageReactionRemove) -> T)

    /**
     * Called when a message has all reactions removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveAll(handler: suspend (MessageReactionRemoveAll) -> T)

    /**
     * Called when a message has all reactions for a single emoji removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveEmoji(handler: suspend (MessageReactionRemoveEmoji) -> T)

    /**
     * Called when a thread has been created.
     *
     * @param handler The created thread.
     */
    @DiskordDsl
    public fun onThreadCreate(handler: suspend (GuildThread) -> T)

    /**
     * Called when a thread has been updated.
     *
     * @param handler The updated thread.
     */
    @DiskordDsl
    public fun onThreadUpdate(handler: suspend (GuildThread) -> T)

    /**
     * Called when a thread has been deleted.
     *
     * @param handler The deleted thread.
     */
    @DiskordDsl
    public fun onThreadDelete(handler: suspend (ThreadDelete) -> T)

    /**
     * Called when added to a channel which has threads.
     *
     * @param handler The thread information.
     */
    @DiskordDsl
    public fun onThreadListSync(handler: suspend (ThreadListSync) -> T)

    /**
     * Called when the thread member object for the current user is updated.
     *
     * @param handler The thread member.
     */
    @DiskordDsl
    public fun onThreadMemberUpdate(handler: suspend (ThreadMember) -> T)

    /**
     * Called when a user is added or removed from a thread.
     *
     * Note: This is associated with the [GatewayIntent.GUILDS] intent and will only
     * show information for the current user.
     *
     * @param handler The updated thread members.
     */
    @DiskordDsl
    public fun onThreadMembersUpdate(handler: suspend (ThreadMembersUpdate) -> T)

    /**
     * Called when a user is added or removed from a thread.
     *
     * Note: This is associated with the privileged [GatewayIntent.GUILD_MEMBERS] intent.
     *
     * @param handler The updated thread members.
     */
    @DiskordDsl
    public fun onThreadMembersUpdatePrivileged(handler: suspend (ThreadMembersUpdate) -> T)

    /**
     * Called when a guild member's presence is updated.
     *
     * @param handler The updated presence.
     */
    @DiskordDsl
    public fun onPresenceUpdate(handler: suspend (PresenceUpdate) -> T)

    /**
     * Called when a user starts typing.
     *
     * @param handler The typing event.
     */
    @DiskordDsl
    public fun onTypingStart(handler: suspend (TypingStart) -> T)

    /**
     * Called when a user is updated.
     *
     * @param handler The updated user.
     */
    @DiskordDsl
    public fun onUserUpdate(handler: suspend (User) -> T)

    /**
     * Called when a joins, leaves, or moves voice channels.
     *
     * @param handler The updated voice state.
     */
    @DiskordDsl
    public fun onVoiceStateUpdate(handler: suspend (VoiceState) -> T)

    /**
     * Called when a voice server is updated.
     *
     * @param handler The updated voice server.
     */
    @DiskordDsl
    public fun onVoiceServerUpdate(handler: suspend (VoiceServerUpdate) -> T)

    /**
     * Called when a guild webhook is updated.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public fun onWebhookUpdate(handler: suspend (WebhookUpdate) -> T)

    /**
     * Called when a new interaction is created.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public fun onInteractionCreate(handler: suspend (Interaction) -> T)

    /**
     * Copies this [EventDispatcher] with a new return type [C]
     */
    public fun <C> forType(): EventDispatcher<C>

    /**
     * Awaits all spawned jobs and returns any results
     */
    public suspend fun handleEvent(event: DiscordEvent, json: JsonElement): List<T>

    public companion object {
        @OptIn(DiskordInternals::class)
        public fun build(coroutineScope: CoroutineScope): EventDispatcher<Unit> = EventDispatcherImpl(coroutineScope)
    }
}

/**
 * Internal implementation of [EventDispatcher]
 */
@DiskordInternals
internal class EventDispatcherImpl<T>(private val dispatcherScope: CoroutineScope) : EventDispatcher<T> {
    private val logger = KotlinLogging.logger {}
    private val listeners: MutableList<(DiscordEvent, JsonElement) -> Deferred<T>?> = mutableListOf()

    override fun onReady(handler: suspend (Ready) -> T) {
        listeners += forEvent(DiscordEvent.READY) {
            handler(defaultJson.decodeFromJsonElement(Ready.serializer(), it))
        }
    }

    override fun onResume(handler: suspend (Resumed) -> T) {
        listeners += forEvent(DiscordEvent.RESUMED) {
            handler(defaultJson.decodeFromJsonElement(Resumed.serializer(), it))
        }
    }

    override fun onChannelCreate(handler: suspend (Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelUpdate(handler: suspend (Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelDelete(handler: suspend (Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_DELETE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelPinsUpdate(handler: suspend (ChannelPinUpdate) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_PINS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ChannelPinUpdate.serializer(), it))
        }
    }

    override fun onGuildCreate(handler: suspend (Guild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Guild.serializer(), it))
        }
    }

    override fun onGuildUpdate(handler: suspend (Guild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Guild.serializer(), it))
        }
    }

    override fun onGuildDelete(handler: suspend (UnavailableGuild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_DELETE) {
            handler(defaultJson.decodeFromJsonElement(UnavailableGuild.serializer(), it))
        }
    }

    override fun onGuildBanAdd(handler: suspend (GuildBan) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_ADD) {
            handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildBanRemove(handler: suspend (GuildBan) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildEmojiUpdate(handler: suspend (GuildEmojiUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_EMOJIS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildEmojiUpdate.serializer(), it))
        }
    }

    override fun onGuildStickersUpdate(handler: suspend (GuildStickersUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_STICKERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildStickersUpdate.serializer(), it))
        }
    }

    override fun onGuildIntegrationsUpdate(handler: suspend (GuildIntegrationUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildIntegrationUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberAdd(handler: suspend (GuildMemberAdd) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_ADD) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberAdd.serializer(), it))
        }
    }

    override fun onGuildMemberUpdate(handler: suspend (GuildMemberUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberRemove(handler: suspend (GuildMemberRemove) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberRemove.serializer(), it))
        }
    }

    override fun onGuildMembersChunk(handler: suspend (GuildMembersChunk) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBERS_CHUNK) {
            handler(defaultJson.decodeFromJsonElement(GuildMembersChunk.serializer(), it))
        }
    }

    override fun onGuildRoleCreate(handler: suspend (GuildRoleCreate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleCreate.serializer(), it))
        }
    }

    override fun onGuildRoleUpdate(handler: suspend (GuildRoleUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleUpdate.serializer(), it))
        }
    }

    override fun onGuildRoleDelete(handler: suspend (GuildRoleDelete) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleDelete.serializer(), it))
        }
    }

    override fun onGuildInviteCreate(handler: suspend (GuildInviteCreate) -> T) {
        listeners += forEvent(DiscordEvent.INVITE_CREATE) {
            handler(defaultJson.decodeFromJsonElement(GuildInviteCreate.serializer(), it))
        }
    }

    override fun onGuildInviteDelete(handler: suspend (GuildInviteDelete) -> T) {
        listeners += forEvent(DiscordEvent.INVITE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(GuildInviteDelete.serializer(), it))
        }
    }

    override fun onMessageCreate(handler: suspend (Message) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageUpdate(handler: suspend (Message) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageDelete(handler: suspend (MessageDelete) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(MessageDelete.serializer(), it))
        }
    }

    override fun onMessageDeleteBulk(handler: suspend (BulkMessageDelete) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE_BULK) {
            handler(defaultJson.decodeFromJsonElement(BulkMessageDelete.serializer(), it))
        }
    }

    override fun onMessageReactionAdd(handler: suspend (MessageReactionAdd) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_ADD) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionAdd.serializer(), it))
        }
    }

    override fun onMessageReactionRemove(handler: suspend (MessageReactionRemove) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemove.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveAll(handler: suspend (MessageReactionRemoveAll) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_ALL) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveAll.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveEmoji(handler: suspend (MessageReactionRemoveEmoji) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveEmoji.serializer(), it))
        }
    }

    override fun onThreadCreate(handler: suspend (GuildThread) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_CREATE) {
            handler(defaultJson.decodeFromJsonElement(GuildThread.serializer(), it))
        }
    }

    override fun onThreadUpdate(handler: suspend (GuildThread) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildThread.serializer(), it))
        }
    }

    override fun onThreadDelete(handler: suspend (ThreadDelete) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_DELETE) {
            handler(defaultJson.decodeFromJsonElement(ThreadDelete.serializer(), it))
        }
    }

    override fun onThreadListSync(handler: suspend (ThreadListSync) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_LIST_SYNC) {
            handler(defaultJson.decodeFromJsonElement(ThreadListSync.serializer(), it))
        }
    }

    override fun onThreadMemberUpdate(handler: suspend (ThreadMember) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMember.serializer(), it))
        }
    }

    override fun onThreadMembersUpdate(handler: suspend (ThreadMembersUpdate) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMembersUpdate.serializer(), it))
        }
    }

    override fun onThreadMembersUpdatePrivileged(handler: suspend (ThreadMembersUpdate) -> T) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMembersUpdate.serializer(), it))
        }
    }

    override fun onPresenceUpdate(handler: suspend (PresenceUpdate) -> T) {
        listeners += forEvent(DiscordEvent.PRESENCE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(PresenceUpdate.serializer(), it))
        }
    }

    override fun onTypingStart(handler: suspend (TypingStart) -> T) {
        listeners += forEvent(DiscordEvent.TYPING_START) {
            handler(defaultJson.decodeFromJsonElement(TypingStart.serializer(), it))
        }
    }

    override fun onUserUpdate(handler: suspend (User) -> T) {
        listeners += forEvent(DiscordEvent.USER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(User.serializer(), it))
        }
    }

    override fun onVoiceStateUpdate(handler: suspend (VoiceState) -> T) {
        listeners += forEvent(DiscordEvent.VOICE_STATE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(VoiceState.serializer(), it))
        }
    }

    override fun onVoiceServerUpdate(handler: suspend (VoiceServerUpdate) -> T) {
        listeners += forEvent(DiscordEvent.VOICE_SERVER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(VoiceServerUpdate.serializer(), it))
        }
    }

    override fun onWebhookUpdate(handler: suspend (WebhookUpdate) -> T) {
        listeners += forEvent(DiscordEvent.WEBHOOKS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(WebhookUpdate.serializer(), it))
        }
    }

    override fun onInteractionCreate(handler: suspend (Interaction) -> T) {
        listeners += forEvent(DiscordEvent.INTERACTION_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Interaction.serializer(), it))
        }
    }

    override fun <C> forType(): EventDispatcher<C> {
        return EventDispatcherImpl(dispatcherScope)
    }

    override suspend fun handleEvent(event: DiscordEvent, json: JsonElement): List<T> {
        return listeners.mapNotNull { it(event, json) }.map { it.await() }
    }

    private fun forEvent(discordEvent: DiscordEvent, block: suspend (JsonElement) -> T): (DiscordEvent, JsonElement) -> Deferred<T>? {
        return { event, json->
            if (event == discordEvent) {
                dispatcherScope.async {
                    try {
                        block(json)
                    } catch (e: Throwable) {
                        logger.warn(e) { "Dispatched event $event caused exception $e" }
                        throw e
                    }
                }
            } else {
                null
            }
        }
    }
}
