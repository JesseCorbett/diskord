package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.TextChannel
import com.jessecorbett.diskord.api.common.Message
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
     * Called when a message has been created.
     *
     * @param handler The created message.
     */
    @DiskordDsl
    public suspend fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T)

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

    override suspend fun onMessageCreate(handler: suspend DispatchBase.(Message) -> T) {
        forEvent(DiscordEvent.MESSAGE_CREATE) {
            results.add(DispatchBase.handler(defaultJson.decodeFromJsonElement(Message.serializer(), data)))
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
