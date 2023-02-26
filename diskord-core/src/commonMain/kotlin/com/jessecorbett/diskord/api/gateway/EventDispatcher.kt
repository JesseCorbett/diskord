package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.BulkMessageDelete
import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.GuildThread
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.common.MessageDelete
import com.jessecorbett.diskord.api.common.TextChannel
import com.jessecorbett.diskord.api.common.ThreadDelete
import com.jessecorbett.diskord.api.common.ThreadListSync
import com.jessecorbett.diskord.api.common.ThreadMember
import com.jessecorbett.diskord.api.common.ThreadMembersUpdate
import com.jessecorbett.diskord.api.common.User
import com.jessecorbett.diskord.api.common.VoiceState
import com.jessecorbett.diskord.api.gateway.events.ChannelPinUpdate
import com.jessecorbett.diskord.api.gateway.events.CreatedGuild
import com.jessecorbett.diskord.api.gateway.events.DiscordEvent
import com.jessecorbett.diskord.api.gateway.events.GuildBan
import com.jessecorbett.diskord.api.gateway.events.GuildEmojiUpdate
import com.jessecorbett.diskord.api.gateway.events.GuildIntegrationUpdate
import com.jessecorbett.diskord.api.gateway.events.GuildInviteCreate
import com.jessecorbett.diskord.api.gateway.events.GuildInviteDelete
import com.jessecorbett.diskord.api.gateway.events.GuildMemberAdd
import com.jessecorbett.diskord.api.gateway.events.GuildMemberRemove
import com.jessecorbett.diskord.api.gateway.events.GuildMemberUpdate
import com.jessecorbett.diskord.api.gateway.events.GuildMembersChunk
import com.jessecorbett.diskord.api.gateway.events.GuildRoleCreate
import com.jessecorbett.diskord.api.gateway.events.GuildRoleDelete
import com.jessecorbett.diskord.api.gateway.events.GuildRoleUpdate
import com.jessecorbett.diskord.api.gateway.events.GuildStickersUpdate
import com.jessecorbett.diskord.api.gateway.events.MessageReactionAdd
import com.jessecorbett.diskord.api.gateway.events.MessageReactionRemove
import com.jessecorbett.diskord.api.gateway.events.MessageReactionRemoveAll
import com.jessecorbett.diskord.api.gateway.events.MessageReactionRemoveEmoji
import com.jessecorbett.diskord.api.gateway.events.PresenceUpdate
import com.jessecorbett.diskord.api.gateway.events.Ready
import com.jessecorbett.diskord.api.gateway.events.Resumed
import com.jessecorbett.diskord.api.gateway.events.TypingStart
import com.jessecorbett.diskord.api.gateway.events.UnavailableGuild
import com.jessecorbett.diskord.api.gateway.events.VoiceServerUpdate
import com.jessecorbett.diskord.api.gateway.events.WebhookUpdate
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import com.jessecorbett.diskord.api.interaction.Interaction
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import mu.KotlinLogging

/**
 * Dispatcher which distributes events to function hooks
 */
@DiskordDsl
public interface EventDispatcher {
    /**
     * Called on the first [Ready] event the bot receives
     *
     * An artificial event, useful for once-per-lifecycle tasks
     */
    public fun onInit(handler: suspend (Ready) -> Unit)

    /**
     * Called when a gateway acknowledges the connection as ready.
     *
     * @param handler Bootstrapping information about the current user.
     */
    @DiskordDsl
    public fun onReady(handler: suspend (Ready) -> Unit)

    /**
     * Called when a gateway acknowledges the connection has resumed.
     *
     * @param handler Resume trace info (not generally useful).
     */
    @DiskordDsl
    public fun onResume(handler: suspend (Resumed) -> Unit)

    /**
     * Called when a channel is created, the current user gets access to a channel, or the current user receives a DM.
     *
     * @param handler The created/received channel.
     */
    @DiskordDsl
    public fun onChannelCreate(handler: suspend (Channel) -> Unit)

    /**
     * Called when a channel is updated. Does not include [TextChannel.lastMessageId] updates.
     *
     * @param handler The updated channel.
     */
    @DiskordDsl
    public fun onChannelUpdate(handler: suspend (Channel) -> Unit)

    /**
     * Called when a channel is deleted.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelDelete(handler: suspend (Channel) -> Unit)

    /**
     * Called when a message is pinned or unpinned in a channel.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelPinsUpdate(handler: suspend (ChannelPinUpdate) -> Unit)

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
    public fun onGuildCreate(handler: suspend (CreatedGuild) -> Unit)

    /**
     * Called when a guild is updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildUpdate(handler: suspend (Guild) -> Unit)

    /**
     * Called when a guild is unavailable or the user is removed.
     *
     * If [UnavailableGuild.unavailable] is not set, the user was removed from the guild.
     *
     * @param handler The unavailable guild.
     */
    @DiskordDsl
    public fun onGuildDelete(handler: suspend (UnavailableGuild) -> Unit)

    /**
     * Called when a user is banned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanAdd(handler: suspend (GuildBan) -> Unit)

    /**
     * Called when a user is unbanned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanRemove(handler: suspend (GuildBan) -> Unit)

    /**
     * Called when a guild's custom emoji have been updated.
     *
     * @param handler The updated emoji.
     */
    @DiskordDsl
    public fun onGuildEmojiUpdate(handler: suspend (GuildEmojiUpdate) -> Unit)

    /**
     * Called when a guild's custom stickers have been updated.
     *
     * @param handler The updated sticker.
     */
    @DiskordDsl
    public fun onGuildStickersUpdate(handler: suspend (GuildStickersUpdate) -> Unit)

    /**
     * Called when a guild's integrations have been updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildIntegrationsUpdate(handler: suspend (GuildIntegrationUpdate) -> Unit)

    /**
     * Called when someone joins a guild
     *
     * @param handler The new guild member.
     */
    @DiskordDsl
    public fun onGuildMemberAdd(handler: suspend (GuildMemberAdd) -> Unit)

    /**
     * Called when a guild membership is updated
     *
     * @param handler The guild member update.
     */
    @DiskordDsl
    public fun onGuildMemberUpdate(handler: suspend (GuildMemberUpdate) -> Unit)

    /**
     * Called when someone is removed from a guild, either from leaving, being kicked, or being banned
     *
     * @param handler The removed guild member.
     */
    @DiskordDsl
    public fun onGuildMemberRemove(handler: suspend (GuildMemberRemove) -> Unit)

    /**
     * Sent in response to Guild Request Members
     *
     * You can use the chunk_index and chunk_count to calculate how many chunks are left for your request.
     *
     * @param handler A requested chunk of guild members.
     */
    @DiskordDsl
    public fun onGuildMembersChunk(handler: suspend (GuildMembersChunk) -> Unit)

    /**
     * Called when a role is created
     *
     * @param handler The created role.
     */
    @DiskordDsl
    public fun onGuildRoleCreate(handler: suspend (GuildRoleCreate) -> Unit)

    /**
     * Called when a role is updated
     *
     * @param handler The updated role.
     */
    @DiskordDsl
    public fun onGuildRoleUpdate(handler: suspend (GuildRoleUpdate) -> Unit)

    /**
     * Called when a role is deleted
     *
     * @param handler The deleted role.
     */
    @DiskordDsl
    public fun onGuildRoleDelete(handler: suspend (GuildRoleDelete) -> Unit)

    /**
     * Called when an invite is created
     *
     * @param handler The created invite.
     */
    @DiskordDsl
    public fun onGuildInviteCreate(handler: suspend (GuildInviteCreate) -> Unit)

    /**
     * Called when an invite is deleted
     *
     * @param handler The deleted invite.
     */
    @DiskordDsl
    public fun onGuildInviteDelete(handler: suspend (GuildInviteDelete) -> Unit)

    /**
     * Called when a message has been created.
     *
     * @param handler The created message.
     */
    @DiskordDsl
    public fun onMessageCreate(handler: suspend (Message) -> Unit)

    /**
     * Called when a message has been updated.
     *
     * @param handler The updated message.
     */
    @DiskordDsl
    public fun onMessageUpdate(handler: suspend (Message) -> Unit)

    /**
     * Called when a message has been deleted.
     *
     * @param handler The deleted message.
     */
    @DiskordDsl
    public fun onMessageDelete(handler: suspend (MessageDelete) -> Unit)

    /**
     * Called when messages have been bulk deleted.
     *
     * @param handler The deleted messages.
     */
    @DiskordDsl
    public fun onMessageDeleteBulk(handler: suspend (BulkMessageDelete) -> Unit)

    /**
     * Called when a message is reacted to.
     *
     * @param handler The added reaction.
     */
    @DiskordDsl
    public fun onMessageReactionAdd(handler: suspend (MessageReactionAdd) -> Unit)

    /**
     * Called when a message reaction is removed.
     *
     * @param handler The removed reaction.
     */
    @DiskordDsl
    public fun onMessageReactionRemove(handler: suspend (MessageReactionRemove) -> Unit)

    /**
     * Called when a message has all reactions removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveAll(handler: suspend (MessageReactionRemoveAll) -> Unit)

    /**
     * Called when a message has all reactions for a single emoji removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveEmoji(handler: suspend (MessageReactionRemoveEmoji) -> Unit)

    /**
     * Called when a thread has been created.
     *
     * @param handler The created thread.
     */
    @DiskordDsl
    public fun onThreadCreate(handler: suspend (GuildThread) -> Unit)

    /**
     * Called when a thread has been updated.
     *
     * @param handler The updated thread.
     */
    @DiskordDsl
    public fun onThreadUpdate(handler: suspend (GuildThread) -> Unit)

    /**
     * Called when a thread has been deleted.
     *
     * @param handler The deleted thread.
     */
    @DiskordDsl
    public fun onThreadDelete(handler: suspend (ThreadDelete) -> Unit)

    /**
     * Called when added to a channel which has threads.
     *
     * @param handler The thread information.
     */
    @DiskordDsl
    public fun onThreadListSync(handler: suspend (ThreadListSync) -> Unit)

    /**
     * Called when the thread member object for the current user is updated.
     *
     * @param handler The thread member.
     */
    @DiskordDsl
    public fun onThreadMemberUpdate(handler: suspend (ThreadMember) -> Unit)

    /**
     * Called when a user is added or removed from a thread.
     *
     * Note: This is associated with the [GatewayIntent.GUILDS] intent and will only
     * show information for the current user.
     *
     * @param handler The updated thread members.
     */
    @DiskordDsl
    public fun onThreadMembersUpdate(handler: suspend (ThreadMembersUpdate) -> Unit)

    /**
     * Called when a user is added or removed from a thread.
     *
     * Note: This is associated with the privileged [GatewayIntent.GUILD_MEMBERS] intent.
     *
     * @param handler The updated thread members.
     */
    @DiskordDsl
    public fun onThreadMembersUpdatePrivileged(handler: suspend (ThreadMembersUpdate) -> Unit)

    /**
     * Called when a guild member's presence is updated.
     *
     * @param handler The updated presence.
     */
    @DiskordDsl
    public fun onPresenceUpdate(handler: suspend (PresenceUpdate) -> Unit)

    /**
     * Called when a user starts typing.
     *
     * @param handler The typing event.
     */
    @DiskordDsl
    public fun onTypingStart(handler: suspend (TypingStart) -> Unit)

    /**
     * Called when a user is updated.
     *
     * @param handler The updated user.
     */
    @DiskordDsl
    public fun onUserUpdate(handler: suspend (User) -> Unit)

    /**
     * Called when a joins, leaves, or moves voice channels.
     *
     * @param handler The updated voice state.
     */
    @DiskordDsl
    public fun onVoiceStateUpdate(handler: suspend (VoiceState) -> Unit)

    /**
     * Called when a voice server is updated.
     *
     * @param handler The updated voice server.
     */
    @DiskordDsl
    public fun onVoiceServerUpdate(handler: suspend (VoiceServerUpdate) -> Unit)

    /**
     * Called when a guild webhook is updated.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public fun onWebhookUpdate(handler: suspend (WebhookUpdate) -> Unit)

    /**
     * Called when a new interaction is created.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public fun onInteractionCreate(handler: suspend (Interaction) -> Unit)

    /**
     * Awaits all spawned jobs and returns any results
     */
    public suspend fun handleEvent(event: DiscordEvent, json: JsonElement)

    public companion object {
        @OptIn(DiskordInternals::class)
        public fun build(coroutineScope: CoroutineScope): EventDispatcher = EventDispatcherImpl(coroutineScope)
    }
}

/**
 * Internal implementation of [EventDispatcher]
 */
@DiskordInternals
internal class EventDispatcherImpl(private val dispatcherScope: CoroutineScope) : EventDispatcher {
    private val logger = KotlinLogging.logger {}
    private val listeners: MutableList<(DiscordEvent, JsonElement) -> Job?> = mutableListOf()

    override fun onInit(handler: suspend (Ready) -> Unit) {
        var hasRan = false
        onReady {
            if (!hasRan) {
                hasRan = true
                handler(it)
            }
        }
    }

    override fun onReady(handler: suspend (Ready) -> Unit) {
        listeners += forEvent(DiscordEvent.READY) {
            handler(defaultJson.decodeFromJsonElement(Ready.serializer(), it))
        }
    }

    override fun onResume(handler: suspend (Resumed) -> Unit) {
        listeners += forEvent(DiscordEvent.RESUMED) {
            handler(defaultJson.decodeFromJsonElement(Resumed.serializer(), it))
        }
    }

    override fun onChannelCreate(handler: suspend (Channel) -> Unit) {
        listeners += forEvent(DiscordEvent.CHANNEL_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelUpdate(handler: suspend (Channel) -> Unit) {
        listeners += forEvent(DiscordEvent.CHANNEL_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelDelete(handler: suspend (Channel) -> Unit) {
        listeners += forEvent(DiscordEvent.CHANNEL_DELETE) {
            handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelPinsUpdate(handler: suspend (ChannelPinUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.CHANNEL_PINS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ChannelPinUpdate.serializer(), it))
        }
    }

    override fun onGuildCreate(handler: suspend (CreatedGuild) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_CREATE) {
            handler(defaultJson.decodeFromJsonElement(CreatedGuild.serializer(), it))
        }
    }

    override fun onGuildUpdate(handler: suspend (Guild) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Guild.serializer(), it))
        }
    }

    override fun onGuildDelete(handler: suspend (UnavailableGuild) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_DELETE) {
            handler(defaultJson.decodeFromJsonElement(UnavailableGuild.serializer(), it))
        }
    }

    override fun onGuildBanAdd(handler: suspend (GuildBan) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_ADD) {
            handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildBanRemove(handler: suspend (GuildBan) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildEmojiUpdate(handler: suspend (GuildEmojiUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_EMOJIS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildEmojiUpdate.serializer(), it))
        }
    }

    override fun onGuildStickersUpdate(handler: suspend (GuildStickersUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_STICKERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildStickersUpdate.serializer(), it))
        }
    }

    override fun onGuildIntegrationsUpdate(handler: suspend (GuildIntegrationUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildIntegrationUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberAdd(handler: suspend (GuildMemberAdd) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_ADD) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberAdd.serializer(), it))
        }
    }

    override fun onGuildMemberUpdate(handler: suspend (GuildMemberUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberRemove(handler: suspend (GuildMemberRemove) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildMemberRemove.serializer(), it))
        }
    }

    override fun onGuildMembersChunk(handler: suspend (GuildMembersChunk) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBERS_CHUNK) {
            handler(defaultJson.decodeFromJsonElement(GuildMembersChunk.serializer(), it))
        }
    }

    override fun onGuildRoleCreate(handler: suspend (GuildRoleCreate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleCreate.serializer(), it))
        }
    }

    override fun onGuildRoleUpdate(handler: suspend (GuildRoleUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleUpdate.serializer(), it))
        }
    }

    override fun onGuildRoleDelete(handler: suspend (GuildRoleDelete) -> Unit) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(GuildRoleDelete.serializer(), it))
        }
    }

    override fun onGuildInviteCreate(handler: suspend (GuildInviteCreate) -> Unit) {
        listeners += forEvent(DiscordEvent.INVITE_CREATE) {
            handler(defaultJson.decodeFromJsonElement(GuildInviteCreate.serializer(), it))
        }
    }

    override fun onGuildInviteDelete(handler: suspend (GuildInviteDelete) -> Unit) {
        listeners += forEvent(DiscordEvent.INVITE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(GuildInviteDelete.serializer(), it))
        }
    }

    override fun onMessageCreate(handler: suspend (Message) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageUpdate(handler: suspend (Message) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageDelete(handler: suspend (MessageDelete) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE) {
            handler(defaultJson.decodeFromJsonElement(MessageDelete.serializer(), it))
        }
    }

    override fun onMessageDeleteBulk(handler: suspend (BulkMessageDelete) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE_BULK) {
            handler(defaultJson.decodeFromJsonElement(BulkMessageDelete.serializer(), it))
        }
    }

    override fun onMessageReactionAdd(handler: suspend (MessageReactionAdd) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_ADD) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionAdd.serializer(), it))
        }
    }

    override fun onMessageReactionRemove(handler: suspend (MessageReactionRemove) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemove.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveAll(handler: suspend (MessageReactionRemoveAll) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_ALL) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveAll.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveEmoji(handler: suspend (MessageReactionRemoveEmoji) -> Unit) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI) {
            handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveEmoji.serializer(), it))
        }
    }

    override fun onThreadCreate(handler: suspend (GuildThread) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_CREATE) {
            handler(defaultJson.decodeFromJsonElement(GuildThread.serializer(), it))
        }
    }

    override fun onThreadUpdate(handler: suspend (GuildThread) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(GuildThread.serializer(), it))
        }
    }

    override fun onThreadDelete(handler: suspend (ThreadDelete) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_DELETE) {
            handler(defaultJson.decodeFromJsonElement(ThreadDelete.serializer(), it))
        }
    }

    override fun onThreadListSync(handler: suspend (ThreadListSync) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_LIST_SYNC) {
            handler(defaultJson.decodeFromJsonElement(ThreadListSync.serializer(), it))
        }
    }

    override fun onThreadMemberUpdate(handler: suspend (ThreadMember) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMember.serializer(), it))
        }
    }

    override fun onThreadMembersUpdate(handler: suspend (ThreadMembersUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMembersUpdate.serializer(), it))
        }
    }

    override fun onThreadMembersUpdatePrivileged(handler: suspend (ThreadMembersUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.THREAD_MEMBERS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(ThreadMembersUpdate.serializer(), it))
        }
    }

    override fun onPresenceUpdate(handler: suspend (PresenceUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.PRESENCE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(PresenceUpdate.serializer(), it))
        }
    }

    override fun onTypingStart(handler: suspend (TypingStart) -> Unit) {
        listeners += forEvent(DiscordEvent.TYPING_START) {
            handler(defaultJson.decodeFromJsonElement(TypingStart.serializer(), it))
        }
    }

    override fun onUserUpdate(handler: suspend (User) -> Unit) {
        listeners += forEvent(DiscordEvent.USER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(User.serializer(), it))
        }
    }

    override fun onVoiceStateUpdate(handler: suspend (VoiceState) -> Unit) {
        listeners += forEvent(DiscordEvent.VOICE_STATE_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(VoiceState.serializer(), it))
        }
    }

    override fun onVoiceServerUpdate(handler: suspend (VoiceServerUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.VOICE_SERVER_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(VoiceServerUpdate.serializer(), it))
        }
    }

    override fun onWebhookUpdate(handler: suspend (WebhookUpdate) -> Unit) {
        listeners += forEvent(DiscordEvent.WEBHOOKS_UPDATE) {
            handler(defaultJson.decodeFromJsonElement(WebhookUpdate.serializer(), it))
        }
    }

    override fun onInteractionCreate(handler: suspend (Interaction) -> Unit) {
        listeners += forEvent(DiscordEvent.INTERACTION_CREATE) {
            handler(defaultJson.decodeFromJsonElement(Interaction.serializer(), it))
        }
    }

    override suspend fun handleEvent(event: DiscordEvent, json: JsonElement) {
        listeners.mapNotNull { it(event, json) }.forEach {
            try {
                it.join()
            } catch (e: kotlinx.coroutines.CancellationException) {
                logger.debug { "a job was cancelled, routine issue we can safely hide from the users" }
            } catch (e: Exception) {
                logger.warn { "Dispatched event $event caused exception $e" }
            }
        }
    }

    private fun forEvent(discordEvent: DiscordEvent, block: suspend (JsonElement) -> Unit): (DiscordEvent, JsonElement) -> Job? {
        return { event, json ->
            if (event == discordEvent) {
                dispatcherScope.launch { block(json) }
            } else {
                null
            }
        }
    }
}
