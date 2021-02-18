package com.jessecorbett.diskord.api.gateway

import com.jessecorbett.diskord.DiskordDsl
import com.jessecorbett.diskord.api.common.Channel
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.gateway.events.*
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
