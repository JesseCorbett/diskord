package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonElement
import mu.KotlinLogging


/**
 * No-op object to enforce DSL syntax and prevent undesireable nesting of on* calls
 */
@DiskordDsl
public object DispatchBase

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
    public fun onReady(handler: suspend DispatchBase.(Ready) -> T)

    /**
     * Called when a gateway acknowledges the connection has resumed.
     *
     * @param handler Resume trace info (not generally useful).
     */
    @DiskordDsl
    public fun onResume(handler: suspend DispatchBase.(Resumed) -> T)

    /**
     * Called when a channel is created, the current user gets access to a channel, or the current user receives a DM.
     *
     * @param handler The created/received channel.
     */
    @DiskordDsl
    public fun onChannelCreate(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a channel is updated. Does not include [TextChannel.lastMessageId] updates.
     *
     * @param handler The updated channel.
     */
    @DiskordDsl
    public fun onChannelUpdate(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a channel is deleted.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelDelete(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a message is pinned or unpinned in a channel.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public fun onChannelPinsUpdate(handler: suspend DispatchBase.(ChannelPinUpdate) -> T)

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
    public fun onGuildCreate(handler: suspend DispatchBase.(Guild) -> T)

    /**
     * Called when a guild is updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildUpdate(handler: suspend DispatchBase.(Guild) -> T)

    /**
     * Called when a guild is unavailable or the user is removed.
     *
     * If [UnavailableGuild.unavailable] is not set, the user was removed from the guild.
     *
     * @param handler The unavailable guild.
     */
    @DiskordDsl
    public fun onGuildDelete(handler: suspend DispatchBase.(UnavailableGuild) -> T)

    /**
     * Called when a user is banned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanAdd(handler: suspend DispatchBase.(GuildBan) -> T)

    /**
     * Called when a user is unbanned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public fun onGuildBanRemove(handler: suspend DispatchBase.(GuildBan) -> T)

    /**
     * Called when a guild's custom emoji have been updated.
     *
     * @param handler The updated emoji.
     */
    @DiskordDsl
    public fun onGuildEmojiUpdate(handler: suspend DispatchBase.(GuildEmojiUpdate) -> T)

    /**
     * Called when a guild's integrations have been updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public fun onGuildIntegrationsUpdate(handler: suspend DispatchBase.(GuildIntegrationUpdate) -> T)

    /**
     * Called when someone joins a guild
     *
     * @param handler The new guild member.
     */
    @DiskordDsl
    public fun onGuildMemberAdd(handler: suspend DispatchBase.(GuildMemberAdd) -> T)

    /**
     * Called when a guild membership is updated
     *
     * @param handler The guild member update.
     */
    @DiskordDsl
    public fun onGuildMemberUpdate(handler: suspend DispatchBase.(GuildMemberUpdate) -> T)

    /**
     * Called when someone is removed from a guild, either from leaving, being kicked, or being banned
     *
     * @param handler The removed guild member.
     */
    @DiskordDsl
    public fun onGuildMemberRemove(handler: suspend DispatchBase.(GuildMemberRemove) -> T)

    /**
     * Sent in response to Guild Request Members
     *
     * You can use the chunk_index and chunk_count to calculate how many chunks are left for your request.
     *
     * @param handler A requested chunk of guild members.
     */
    @DiskordDsl
    public fun onGuildMembersChunk(handler: suspend DispatchBase.(GuildMembersChunk) -> T)

    /**
     * Called when a role is created
     *
     * @param handler The created role.
     */
    @DiskordDsl
    public fun onGuildRoleCreate(handler: suspend DispatchBase.(GuildRoleCreate) -> T)

    /**
     * Called when a role is updated
     *
     * @param handler The updated role.
     */
    @DiskordDsl
    public fun onGuildRoleUpdate(handler: suspend DispatchBase.(GuildRoleUpdate) -> T)

    /**
     * Called when a role is deleted
     *
     * @param handler The deleted role.
     */
    @DiskordDsl
    public fun onGuildRoleDelete(handler: suspend DispatchBase.(GuildRoleDelete) -> T)

    /**
     * Called when an invite is created
     *
     * @param handler The created invite.
     */
    @DiskordDsl
    public fun onGuildInviteCreate(handler: suspend DispatchBase.(GuildInviteCreate) -> T)

    /**
     * Called when an invite is deleted
     *
     * @param handler The deleted invite.
     */
    @DiskordDsl
    public fun onGuildInviteDelete(handler: suspend DispatchBase.(GuildInviteDelete) -> T)

    /**
     * Called when a message has been created.
     *
     * @param handler The created message.
     */
    @DiskordDsl
    public fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T)

    /**
     * Called when a message has been updated.
     *
     * @param handler The updated message.
     */
    @DiskordDsl
    public fun onMessageUpdate(handler: suspend DispatchBase.(Message) -> T)

    /**
     * Called when a message has been deleted.
     *
     * @param handler The deleted message.
     */
    @DiskordDsl
    public fun onMessageDelete(handler: suspend DispatchBase.(MessageDelete) -> T)

    /**
     * Called when messages have been bulk deleted.
     *
     * @param handler The deleted messages.
     */
    @DiskordDsl
    public fun onMessageDeleteBulk(handler: suspend DispatchBase.(BulkMessageDelete) -> T)

    /**
     * Called when a message is reacted to.
     *
     * @param handler The added reaction.
     */
    @DiskordDsl
    public fun onMessageReactionAdd(handler: suspend DispatchBase.(MessageReactionAdd) -> T)

    /**
     * Called when a message reaction is removed.
     *
     * @param handler The removed reaction.
     */
    @DiskordDsl
    public fun onMessageReactionRemove(handler: suspend DispatchBase.(MessageReactionRemove) -> T)

    /**
     * Called when a message has all reactions removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveAll(handler: suspend DispatchBase.(MessageReactionRemoveAll) -> T)

    /**
     * Called when a message has all reactions for a single emoji removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public fun onMessageReactionRemoveEmoji(handler: suspend DispatchBase.(MessageReactionRemoveEmoji) -> T)

    /**
     * Called when a guild member's presence is updated.
     *
     * @param handler The updated presence.
     */
    @DiskordDsl
    public fun onPresenceUpdate(handler: suspend DispatchBase.(PresenceUpdate) -> T)

    /**
     * Called when a user starts typing.
     *
     * @param handler The typing event.
     */
    @DiskordDsl
    public fun onTypingStart(handler: suspend DispatchBase.(TypingStart) -> T)

    /**
     * Called when a user is updated.
     *
     * @param handler The updated user.
     */
    @DiskordDsl
    public fun onUserUpdate(handler: suspend DispatchBase.(User) -> T)

    /**
     * Called when a joins, leaves, or moves voice channels.
     *
     * @param handler The updated voice state.
     */
    @DiskordDsl
    public fun onVoiceStateUpdate(handler: suspend DispatchBase.(VoiceState) -> T)

    /**
     * Called when a voice server is updated.
     *
     * @param handler The updated voice server.
     */
    @DiskordDsl
    public fun onVoiceServerUpdate(handler: suspend DispatchBase.(VoiceServerUpdate) -> T)

    /**
     * Called when a guild webhook is updated.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public fun onWebhookUpdate(handler: suspend DispatchBase.(WebhookUpdate) -> T)

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

    override fun onReady(handler: suspend DispatchBase.(Ready) -> T) {
        listeners += forEvent(DiscordEvent.READY) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Ready.serializer(), it))
        }
    }

    override fun onResume(handler: suspend DispatchBase.(Resumed) -> T) {
        listeners += forEvent(DiscordEvent.RESUMED) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Resumed.serializer(), it))
        }
    }

    override fun onChannelCreate(handler: suspend DispatchBase.(Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_CREATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelUpdate(handler: suspend DispatchBase.(Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelDelete(handler: suspend DispatchBase.(Channel) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_DELETE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), it))
        }
    }

    override fun onChannelPinsUpdate(handler: suspend DispatchBase.(ChannelPinUpdate) -> T) {
        listeners += forEvent(DiscordEvent.CHANNEL_PINS_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(ChannelPinUpdate.serializer(), it))
        }
    }

    override fun onGuildCreate(handler: suspend DispatchBase.(Guild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_CREATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Guild.serializer(), it))
        }
    }

    override fun onGuildUpdate(handler: suspend DispatchBase.(Guild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Guild.serializer(), it))
        }
    }

    override fun onGuildDelete(handler: suspend DispatchBase.(UnavailableGuild) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_DELETE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(UnavailableGuild.serializer(), it))
        }
    }

    override fun onGuildBanAdd(handler: suspend DispatchBase.(GuildBan) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_ADD) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildBanRemove(handler: suspend DispatchBase.(GuildBan) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), it))
        }
    }

    override fun onGuildEmojiUpdate(handler: suspend DispatchBase.(GuildEmojiUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildEmojiUpdate.serializer(), it))
        }
    }

    override fun onGuildIntegrationsUpdate(handler: suspend DispatchBase.(GuildIntegrationUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildIntegrationUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberAdd(handler: suspend DispatchBase.(GuildMemberAdd) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_ADD) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberAdd.serializer(), it))
        }
    }

    override fun onGuildMemberUpdate(handler: suspend DispatchBase.(GuildMemberUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberUpdate.serializer(), it))
        }
    }

    override fun onGuildMemberRemove(handler: suspend DispatchBase.(GuildMemberRemove) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBER_REMOVE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberRemove.serializer(), it))
        }
    }

    override fun onGuildMembersChunk(handler: suspend DispatchBase.(GuildMembersChunk) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_MEMBERS_CHUNK) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMembersChunk.serializer(), it))
        }
    }

    override fun onGuildRoleCreate(handler: suspend DispatchBase.(GuildRoleCreate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleCreate.serializer(), it))
        }
    }

    override fun onGuildRoleUpdate(handler: suspend DispatchBase.(GuildRoleUpdate) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleUpdate.serializer(), it))
        }
    }

    override fun onGuildRoleDelete(handler: suspend DispatchBase.(GuildRoleDelete) -> T) {
        listeners += forEvent(DiscordEvent.GUILD_ROLE_DELETE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleDelete.serializer(), it))
        }
    }

    override fun onGuildInviteCreate(handler: suspend DispatchBase.(GuildInviteCreate) -> T) {
        listeners += forEvent(DiscordEvent.INVITE_CREATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildInviteCreate.serializer(), it))
        }
    }

    override fun onGuildInviteDelete(handler: suspend DispatchBase.(GuildInviteDelete) -> T) {
        listeners += forEvent(DiscordEvent.INVITE_DELETE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildInviteDelete.serializer(), it))
        }
    }

    override fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_CREATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageUpdate(handler: suspend DispatchBase.(Message) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(Message.serializer(), it))
        }
    }

    override fun onMessageDelete(handler: suspend DispatchBase.(MessageDelete) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageDelete.serializer(), it))
        }
    }

    override fun onMessageDeleteBulk(handler: suspend DispatchBase.(BulkMessageDelete) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_DELETE_BULK) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(BulkMessageDelete.serializer(), it))
        }
    }

    override fun onMessageReactionAdd(handler: suspend DispatchBase.(MessageReactionAdd) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_ADD) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionAdd.serializer(), it))
        }
    }

    override fun onMessageReactionRemove(handler: suspend DispatchBase.(MessageReactionRemove) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemove.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveAll(handler: suspend DispatchBase.(MessageReactionRemoveAll) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_ALL) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveAll.serializer(), it))
        }
    }

    override fun onMessageReactionRemoveEmoji(handler: suspend DispatchBase.(MessageReactionRemoveEmoji) -> T) {
        listeners += forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveEmoji.serializer(), it))
        }
    }

    override fun onPresenceUpdate(handler: suspend DispatchBase.(PresenceUpdate) -> T) {
        listeners += forEvent(DiscordEvent.PRESENCE_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(PresenceUpdate.serializer(), it))
        }
    }

    override fun onTypingStart(handler: suspend DispatchBase.(TypingStart) -> T) {
        listeners += forEvent(DiscordEvent.TYPING_START) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(TypingStart.serializer(), it))
        }
    }

    override fun onUserUpdate(handler: suspend DispatchBase.(User) -> T) {
        listeners += forEvent(DiscordEvent.USER_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(User.serializer(), it))
        }
    }

    override fun onVoiceStateUpdate(handler: suspend DispatchBase.(VoiceState) -> T) {
        listeners += forEvent(DiscordEvent.VOICE_STATE_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(VoiceState.serializer(), it))
        }
    }

    override fun onVoiceServerUpdate(handler: suspend DispatchBase.(VoiceServerUpdate) -> T) {
        listeners += forEvent(DiscordEvent.VOICE_SERVER_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(VoiceServerUpdate.serializer(), it))
        }
    }

    override fun onWebhookUpdate(handler: suspend DispatchBase.(WebhookUpdate) -> T) {
        listeners += forEvent(DiscordEvent.WEBHOOKS_UPDATE) {
            DispatchBase.handler(defaultJson.decodeFromJsonElement(WebhookUpdate.serializer(), it))
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
