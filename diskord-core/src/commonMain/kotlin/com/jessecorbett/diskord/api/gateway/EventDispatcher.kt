package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.gateway.model.GatewayIntent
import com.jessecorbett.diskord.util.DiskordInternals
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import mu.KotlinLogging

/**
 * Function for setting up event listeners
 */
public typealias EventHandler = suspend EventDispatcher<Unit>.() -> Unit

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
    public suspend fun onReady(handler: suspend DispatchBase.(Ready) -> T)

    /**
     * Called when a gateway acknowledges the connection has resumed.
     *
     * @param handler Resume trace info (not generally useful).
     */
    @DiskordDsl
    public suspend fun onResume(handler: suspend DispatchBase.(Resumed) -> T)

    /**
     * Called when a channel is created, the current user gets access to a channel, or the current user receives a DM.
     *
     * @param handler The created/received channel.
     */
    @DiskordDsl
    public suspend fun onChannelCreate(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a channel is updated. Does not include [TextChannel.lastMessageId] updates.
     *
     * @param handler The updated channel.
     */
    @DiskordDsl
    public suspend fun onChannelUpdate(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a channel is deleted.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public suspend fun onChannelDelete(handler: suspend DispatchBase.(Channel) -> T)

    /**
     * Called when a message is pinned or unpinned in a channel.
     *
     * @param handler The deleted channel.
     */
    @DiskordDsl
    public suspend fun onChannelPinsUpdate(handler: suspend DispatchBase.(ChannelPinUpdate) -> T)

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
    public suspend fun onGuildCreate(handler: suspend DispatchBase.(Guild) -> T)

    /**
     * Called when a guild is updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public suspend fun onGuildUpdate(handler: suspend DispatchBase.(Guild) -> T)

    /**
     * Called when a guild is unavailable or the user is removed.
     *
     * If [UnavailableGuild.unavailable] is not set, the user was removed from the guild.
     *
     * @param handler The unavailable guild.
     */
    @DiskordDsl
    public suspend fun onGuildDelete(handler: suspend DispatchBase.(UnavailableGuild) -> T)

    /**
     * Called when a user is banned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public suspend fun onGuildBanAdd(handler: suspend DispatchBase.(GuildBan) -> T)

    /**
     * Called when a user is unbanned from a guild.
     *
     * @param handler The ban.
     */
    @DiskordDsl
    public suspend fun onGuildBanRemove(handler: suspend DispatchBase.(GuildBan) -> T)

    /**
     * Called when a guild's custom emoji have been updated.
     *
     * @param handler The updated emoji.
     */
    @DiskordDsl
    public suspend fun onGuildEmojiUpdate(handler: suspend DispatchBase.(GuildEmojiUpdate) -> T)

    /**
     * Called when a guild's integrations have been updated.
     *
     * @param handler The updated guild.
     */
    @DiskordDsl
    public suspend fun onGuildIntegrationsUpdate(handler: suspend DispatchBase.(GuildIntegrationUpdate) -> T)

    /**
     * Called when someone joins a guild
     *
     * @param handler The new guild member.
     */
    @DiskordDsl
    public suspend fun onGuildMemberAdd(handler: suspend DispatchBase.(GuildMemberAdd) -> T)

    /**
     * Called when a guild membership is updated
     *
     * @param handler The guild member update.
     */
    @DiskordDsl
    public suspend fun onGuildMemberUpdate(handler: suspend DispatchBase.(GuildMemberUpdate) -> T)

    /**
     * Called when someone is removed from a guild, either from leaving, being kicked, or being banned
     *
     * @param handler The removed guild member.
     */
    @DiskordDsl
    public suspend fun onGuildMemberRemove(handler: suspend DispatchBase.(GuildMemberRemove) -> T)

    /**
     * Sent in response to Guild Request Members
     *
     * You can use the chunk_index and chunk_count to calculate how many chunks are left for your request.
     *
     * @param handler A requested chunk of guild members.
     */
    @DiskordDsl
    public suspend fun onGuildMembersChunk(handler: suspend DispatchBase.(GuildMembersChunk) -> T)

    /**
     * Called when a role is created
     *
     * @param handler The created role.
     */
    @DiskordDsl
    public suspend fun onGuildRoleCreate(handler: suspend DispatchBase.(GuildRoleCreate) -> T)

    /**
     * Called when a role is updated
     *
     * @param handler The updated role.
     */
    @DiskordDsl
    public suspend fun onGuildRoleUpdate(handler: suspend DispatchBase.(GuildRoleUpdate) -> T)

    /**
     * Called when a role is deleted
     *
     * @param handler The deleted role.
     */
    @DiskordDsl
    public suspend fun onGuildRoleDelete(handler: suspend DispatchBase.(GuildRoleDelete) -> T)

    /**
     * Called when an invite is created
     *
     * @param handler The created invite.
     */
    @DiskordDsl
    public suspend fun onGuildInviteCreate(handler: suspend DispatchBase.(GuildInviteCreate) -> T)

    /**
     * Called when an invite is deleted
     *
     * @param handler The deleted invite.
     */
    @DiskordDsl
    public suspend fun onGuildInviteDelete(handler: suspend DispatchBase.(GuildInviteDelete) -> T)

    /**
     * Called when a message has been created.
     *
     * @param handler The created message.
     */
    @DiskordDsl
    public suspend fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T)

    /**
     * Called when a message has been updated.
     *
     * @param handler The updated message.
     */
    @DiskordDsl
    public suspend fun onMessageUpdate(handler: suspend DispatchBase.(Message) -> T)

    /**
     * Called when a message has been deleted.
     *
     * @param handler The deleted message.
     */
    @DiskordDsl
    public suspend fun onMessageDelete(handler: suspend DispatchBase.(MessageDelete) -> T)

    /**
     * Called when messages have been bulk deleted.
     *
     * @param handler The deleted messages.
     */
    @DiskordDsl
    public suspend fun onMessageDeleteBulk(handler: suspend DispatchBase.(BulkMessageDelete) -> T)

    /**
     * Called when a message is reacted to.
     *
     * @param handler The added reaction.
     */
    @DiskordDsl
    public suspend fun onMessageReactionAdd(handler: suspend DispatchBase.(MessageReactionAdd) -> T)

    /**
     * Called when a message reaction is removed.
     *
     * @param handler The removed reaction.
     */
    @DiskordDsl
    public suspend fun onMessageReactionRemove(handler: suspend DispatchBase.(MessageReactionRemove) -> T)

    /**
     * Called when a message has all reactions removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public suspend fun onMessageReactionRemoveAll(handler: suspend DispatchBase.(MessageReactionRemoveAll) -> T)

    /**
     * Called when a message has all reactions for a single emoji removed.
     *
     * @param handler The removed reactions.
     */
    @DiskordDsl
    public suspend fun onMessageReactionRemoveEmoji(handler: suspend DispatchBase.(MessageReactionRemoveEmoji) -> T)

    /**
     * Called when a guild member's presence is updated.
     *
     * @param handler The updated presence.
     */
    @DiskordDsl
    public suspend fun onPresenceUpdate(handler: suspend DispatchBase.(PresenceUpdate) -> T)

    /**
     * Called when a user starts typing.
     *
     * @param handler The typing event.
     */
    @DiskordDsl
    public suspend fun onTypingStart(handler: suspend DispatchBase.(TypingStart) -> T)

    /**
     * Called when a user is updated.
     *
     * @param handler The updated user.
     */
    @DiskordDsl
    public suspend fun onUserUpdate(handler: suspend DispatchBase.(User) -> T)

    /**
     * Called when a joins, leaves, or moves voice channels.
     *
     * @param handler The updated voice state.
     */
    @DiskordDsl
    public suspend fun onVoiceStateUpdate(handler: suspend DispatchBase.(VoiceState) -> T)

    /**
     * Called when a voice server is updated.
     *
     * @param handler The updated voice server.
     */
    @DiskordDsl
    public suspend fun onVoiceServerUpdate(handler: suspend DispatchBase.(VoiceServerUpdate) -> T)

    /**
     * Called when a guild webhook is updated.
     *
     * @param handler The updated webhook.
     */
    @DiskordDsl
    public suspend fun onWebhookUpdate(handler: suspend DispatchBase.(WebhookUpdate) -> T)

    /**
     * Copies this [EventDispatcher] with a new return type [C]
     */
    public fun <C> forType(): EventDispatcher<C>

    /**
     * Awaits all spawned jobs and returns any results
     */
    public suspend fun await(): List<T>
}

/**
 * Internal implementation of [EventDispatcher]
 */
@DiskordInternals
internal class EventDispatcherImpl<T>(
    private val dispatcherScope: CoroutineScope,
    private val event: DiscordEvent,
    private val data: JsonElement
) : EventDispatcher<T> {
    private val logger = KotlinLogging.logger {}
    private val jobs: MutableList<Job> = mutableListOf()
    private val results: MutableList<T> = mutableListOf()

    override suspend fun onReady(handler: suspend DispatchBase.(Ready) -> T) {
        forEvent(DiscordEvent.READY) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Ready.serializer(), data)))
        }
    }

    override suspend fun onResume(handler: suspend DispatchBase.(Resumed) -> T) {
        forEvent(DiscordEvent.RESUMED) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Resumed.serializer(), data)))
        }
    }

    override suspend fun onChannelCreate(handler: suspend DispatchBase.(Channel) -> T) {
        forEvent(DiscordEvent.CHANNEL_CREATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), data)))
        }
    }

    override suspend fun onChannelUpdate(handler: suspend DispatchBase.(Channel) -> T) {
        forEvent(DiscordEvent.CHANNEL_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), data)))
        }
    }

    override suspend fun onChannelDelete(handler: suspend DispatchBase.(Channel) -> T) {
        forEvent(DiscordEvent.CHANNEL_DELETE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Channel.serializer(), data)))
        }
    }

    override suspend fun onChannelPinsUpdate(handler: suspend DispatchBase.(ChannelPinUpdate) -> T) {
        forEvent(DiscordEvent.CHANNEL_PINS_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(ChannelPinUpdate.serializer(), data)))
        }
    }

    override suspend fun onGuildCreate(handler: suspend DispatchBase.(Guild) -> T) {
        forEvent(DiscordEvent.GUILD_CREATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Guild.serializer(), data)))
        }
    }

    override suspend fun onGuildUpdate(handler: suspend DispatchBase.(Guild) -> T) {
        forEvent(DiscordEvent.GUILD_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Guild.serializer(), data)))
        }
    }

    override suspend fun onGuildDelete(handler: suspend DispatchBase.(UnavailableGuild) -> T) {
        forEvent(DiscordEvent.GUILD_DELETE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(UnavailableGuild.serializer(), data)))
        }
    }

    override suspend fun onGuildBanAdd(handler: suspend DispatchBase.(GuildBan) -> T) {
        forEvent(DiscordEvent.GUILD_BAN_ADD) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), data)))
        }
    }

    override suspend fun onGuildBanRemove(handler: suspend DispatchBase.(GuildBan) -> T) {
        forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildBan.serializer(), data)))
        }
    }

    override suspend fun onGuildEmojiUpdate(handler: suspend DispatchBase.(GuildEmojiUpdate) -> T) {
        forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildEmojiUpdate.serializer(), data)))
        }
    }

    override suspend fun onGuildIntegrationsUpdate(handler: suspend DispatchBase.(GuildIntegrationUpdate) -> T) {
        forEvent(DiscordEvent.GUILD_INTEGRATIONS_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildIntegrationUpdate.serializer(), data)))
        }
    }

    override suspend fun onGuildMemberAdd(handler: suspend DispatchBase.(GuildMemberAdd) -> T) {
        forEvent(DiscordEvent.GUILD_MEMBER_ADD) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberAdd.serializer(), data)))
        }
    }

    override suspend fun onGuildMemberUpdate(handler: suspend DispatchBase.(GuildMemberUpdate) -> T) {
        forEvent(DiscordEvent.GUILD_MEMBER_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberUpdate.serializer(), data)))
        }
    }

    override suspend fun onGuildMemberRemove(handler: suspend DispatchBase.(GuildMemberRemove) -> T) {
        forEvent(DiscordEvent.GUILD_MEMBER_REMOVE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMemberRemove.serializer(), data)))
        }
    }

    override suspend fun onGuildMembersChunk(handler: suspend DispatchBase.(GuildMembersChunk) -> T) {
        forEvent(DiscordEvent.GUILD_MEMBERS_CHUNK) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildMembersChunk.serializer(), data)))
        }
    }

    override suspend fun onGuildRoleCreate(handler: suspend DispatchBase.(GuildRoleCreate) -> T) {
        forEvent(DiscordEvent.GUILD_BAN_REMOVE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleCreate.serializer(), data)))
        }
    }

    override suspend fun onGuildRoleUpdate(handler: suspend DispatchBase.(GuildRoleUpdate) -> T) {
        forEvent(DiscordEvent.GUILD_ROLE_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleUpdate.serializer(), data)))
        }
    }

    override suspend fun onGuildRoleDelete(handler: suspend DispatchBase.(GuildRoleDelete) -> T) {
        forEvent(DiscordEvent.GUILD_ROLE_DELETE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildRoleDelete.serializer(), data)))
        }
    }

    override suspend fun onGuildInviteCreate(handler: suspend DispatchBase.(GuildInviteCreate) -> T) {
        forEvent(DiscordEvent.INVITE_CREATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildInviteCreate.serializer(), data)))
        }
    }

    override suspend fun onGuildInviteDelete(handler: suspend DispatchBase.(GuildInviteDelete) -> T) {
        forEvent(DiscordEvent.INVITE_DELETE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(GuildInviteDelete.serializer(), data)))
        }
    }

    override suspend fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T) {
        forEvent(DiscordEvent.MESSAGE_CREATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Message.serializer(), data)))
        }
    }

    override suspend fun onMessageUpdate(handler: suspend DispatchBase.(Message) -> T) {
        forEvent(DiscordEvent.MESSAGE_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Message.serializer(), data)))
        }
    }

    override suspend fun onMessageDelete(handler: suspend DispatchBase.(MessageDelete) -> T) {
        forEvent(DiscordEvent.MESSAGE_DELETE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageDelete.serializer(), data)))
        }
    }

    override suspend fun onMessageDeleteBulk(handler: suspend DispatchBase.(BulkMessageDelete) -> T) {
        forEvent(DiscordEvent.MESSAGE_DELETE_BULK) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(BulkMessageDelete.serializer(), data)))
        }
    }

    override suspend fun onMessageReactionAdd(handler: suspend DispatchBase.(MessageReactionAdd) -> T) {
        forEvent(DiscordEvent.MESSAGE_REACTION_ADD) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionAdd.serializer(), data)))
        }
    }

    override suspend fun onMessageReactionRemove(handler: suspend DispatchBase.(MessageReactionRemove) -> T) {
        forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemove.serializer(), data)))
        }
    }

    override suspend fun onMessageReactionRemoveAll(handler: suspend DispatchBase.(MessageReactionRemoveAll) -> T) {
        forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_ALL) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveAll.serializer(), data)))
        }
    }

    override suspend fun onMessageReactionRemoveEmoji(handler: suspend DispatchBase.(MessageReactionRemoveEmoji) -> T) {
        forEvent(DiscordEvent.MESSAGE_REACTION_REMOVE_EMOJI) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(MessageReactionRemoveEmoji.serializer(), data)))
        }
    }

    override suspend fun onPresenceUpdate(handler: suspend DispatchBase.(PresenceUpdate) -> T) {
        forEvent(DiscordEvent.PRESENCE_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(PresenceUpdate.serializer(), data)))
        }
    }

    override suspend fun onTypingStart(handler: suspend DispatchBase.(TypingStart) -> T) {
        forEvent(DiscordEvent.TYPING_START) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(TypingStart.serializer(), data)))
        }
    }

    override suspend fun onUserUpdate(handler: suspend DispatchBase.(User) -> T) {
        forEvent(DiscordEvent.USER_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(User.serializer(), data)))
        }
    }

    override suspend fun onVoiceStateUpdate(handler: suspend DispatchBase.(VoiceState) -> T) {
        forEvent(DiscordEvent.VOICE_STATE_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(VoiceState.serializer(), data)))
        }
    }

    override suspend fun onVoiceServerUpdate(handler: suspend DispatchBase.(VoiceServerUpdate) -> T) {
        forEvent(DiscordEvent.VOICE_SERVER_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(VoiceServerUpdate.serializer(), data)))
        }
    }

    override suspend fun onWebhookUpdate(handler: suspend DispatchBase.(WebhookUpdate) -> T) {
        forEvent(DiscordEvent.WEBHOOKS_UPDATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(WebhookUpdate.serializer(), data)))
        }
    }

    override fun <C> forType(): EventDispatcher<C> {
        return EventDispatcherImpl(dispatcherScope, event, data)
    }

    override suspend fun await(): List<T> {
        jobs.forEach { it.join() }
        return results
    }

    private suspend fun forEvent(discordEvent: DiscordEvent, block: suspend () -> Unit) {
        if (event == discordEvent) {
            jobs += dispatcherScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    logger.warn(e) { "Dispatched event $event caused exception $e" }
                }
            }
        }
    }
}
