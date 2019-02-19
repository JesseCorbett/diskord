package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.DiscordWebSocket
import com.jessecorbett.diskord.api.websocket.events.*
import com.jessecorbett.diskord.util.EnhancedEventListener
import kotlinx.serialization.json.JsonElement

/**
 * Marks a class or function as belonging to the Diskord DSL.
 */
@DslMarker
annotation class DiskordDsl


/**
 * A bot implementation built using [EnhancedEventListener] and providing a DSL for consuming and acting on events.
 *
 * The usage principle behind this class is assuming reasonable defaults for threading and resource management,
 * allowing the developer to focus on building a bot which reacts to events witnessed by the bot. By design there
 * is the ability to attach an arbitrary number of hooks to any event listener via DSL so that plugins such as the
 * command DSL (via the [commands] and [command] functions) can be added via extension functions without needing to
 * modify or interrupt this base class.
 *
 * @param token The bot token from the discord application management page https://discordapp.com/developers/applications/.
 * @param autoStart Automatically start the bot. Defaults to true.
 * @param shardId The shard id, if this bot is sharded.
 * @param shardCount The count of shards, if the bot is sharded.
 */
class Bot(token: String, autoStart: Boolean = true, shardId: Int = 0, shardCount: Int = 0) : EnhancedEventListener(token) {
    private val websocket = DiscordWebSocket(token, this, autoStart, shardId = shardId, shardCount = shardCount)

    /**
     * Indicates if this bot currently has an active websocket connection.
     */
    val active: Boolean
        get() = websocket.active

    /*
     * Convenience methods for bot implementations
     */

    /**
     * Starts the websocket connection.
     *
     * Only necessary if autostart is disabled. Functionally identical to [Bot.restart]
     */
    fun start() = websocket.start()

    /**
     * Shuts down the bot.
     *
     * @param forceClose Forces closed the bot. False by default. Only set to true if this is the only bot in this instance
     * as it force closes the http client shared by all [DiscordWebSocket] and REST client instances.
     */
    fun shutdown(forceClose: Boolean = false) = websocket.close(forceClose)

    /**
     * Restarts the websocket connection.
     *
     * Persists the session so that any events which occur during the restart should still be received.
     */
    fun restart() = websocket.restart()

    /*
     * DSL mappings of the EventListener
     */

    /**
     * Registers a lambda to be called when any event happens, passing the [DiscordEvent] and the data in JSON string format.
     *
     * @param block The lambda to call, passing the event enum and JSON string.
     */
    @DiskordDsl
    fun anyEvent(block: suspend (DiscordEvent, JsonElement) -> Unit) { anyEventHooks += block }
    private val anyEventHooks: MutableList<suspend (DiscordEvent, JsonElement) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onEvent(event: DiscordEvent, data: JsonElement) { anyEventHooks.forEach { it(event, data) } }

    /**
     * Registers a lambda to be called when the connection has reached a [DiscordEvent.READY] state.
     *
     * @param block The lambda to call, passing the [Ready] event.
     */
    @DiskordDsl
    fun started(block: suspend (Ready) -> Unit) { readyHooks += block }
    private val readyHooks: MutableList<suspend (Ready) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onReady(ready: Ready) { readyHooks.forEach { it(ready) } }

    /**
     * Registers a lambda to be called when the connection has reached a [DiscordEvent.RESUMED] state.
     *
     * @param block The lambda to call, passing the [Resumed] event.
     */
    @DiskordDsl
    fun resumed(block: suspend (Resumed) -> Unit) { resumedHooks += block }
    private val resumedHooks: MutableList<suspend (Resumed) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onResumed(resumed: Resumed) { resumedHooks.forEach { it(resumed) } }

    /**
     * Registers a lambda to be called when a [Channel] has been created.
     *
     * Also gets called for when a DM is received.
     * TODO: Validate this is still accurate.
     *
     * @param block The lambda to call, passing the [Channel] that was created.
     */
    @DiskordDsl
    fun channelCreated(block: suspend (Channel) -> Unit) { channelCreateHooks += block }
    private val channelCreateHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onChannelCreate(channel: Channel) { channelCreateHooks.forEach { it(channel) } }

    /**
     * Registers a lambda to be called when a [Channel] has been updated.
     *
     * @param block The lambda to call, passing the [Channel] that was updated.
     */
    @DiskordDsl
    fun channelUpdated(block: suspend (Channel) -> Unit) { channelUpdateHooks += block }
    private val channelUpdateHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onChannelUpdate(channel: Channel) { channelUpdateHooks.forEach { it(channel) } }

    /**
     * Registers a lambda to be called when a [Channel] is deleted.
     *
     * @param block The lambda to call, passing the [Channel] that was deleted.
     */
    @DiskordDsl
    fun channelDeleted(block: suspend (Channel) -> Unit) { channelDeleteHooks += block }
    private val channelDeleteHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onChannelDelete(channel: Channel) { channelDeleteHooks.forEach { it(channel) } }

    /**
     * Registers a lambda to be called when the pins in a channel are updated.
     *
     * @param block The lambda to call, passing the [ChannelPinUpdate].
     */
    @DiskordDsl
    fun pinsUpdated(block: suspend (ChannelPinUpdate) -> Unit) { channelPinsUpdateHooks += block }
    private val channelPinsUpdateHooks: MutableList<suspend (ChannelPinUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate)  { channelPinsUpdateHooks.forEach { it(channelPinUpdate) } }

    /**
     * Registers a lambda to be called when a [Guild] is created, the user joins a guild, or lazy loading guilds just after [DiscordEvent.READY].
     *
     * @param block The lambda to call, passing the [MessageDelete].
     */
    // TODO: Cache this perhaps, should we want to store this
    @DiskordDsl
    fun guildCreated(block: suspend (CreatedGuild) -> Unit) { guildCreateHooks += block }
    private val guildCreateHooks: MutableList<suspend (CreatedGuild) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildCreate(guild: CreatedGuild) { guildCreateHooks.forEach { it(guild) } }

    /**
     * Registers a lambda to be called when a [Guild] is updated.
     *
     * @param block The lambda to call, passing the updated [Guild].
     */
    @DiskordDsl
    fun guildUpdated(block: suspend (Guild) -> Unit) { guildUpdateHooks += block }
    private val guildUpdateHooks: MutableList<suspend (Guild) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildUpdate(guild: Guild) { guildUpdateHooks.forEach { it(guild) } }

    /**
     * Registers a lambda to be called when a [Guild] is deleted.
     *
     * @param block The lambda to call, passing the [Guild] that was deleted.
     */
    @DiskordDsl
    fun guildDeleted(block: suspend (Guild) -> Unit) { guildDeleteHooks += block }
    private val guildDeleteHooks: MutableList<suspend (Guild) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildDelete(guild: Guild) { guildDeleteHooks.forEach { it(guild) } }

    /**
     * Registers a lambda to be called when a chunk of guild members is received.
     *
     * @param block The lambda to call, passing the [GuildMembersChunk].
     */
    // TODO: Cache this perhaps, should we want to store this
    @DiskordDsl
    fun guildMemberChunkReceived(block: suspend (GuildMembersChunk) -> Unit) { guildMemberChunkHooks += block }
    private val guildMemberChunkHooks: MutableList<suspend (GuildMembersChunk) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildMemberChunk(guildMembers: GuildMembersChunk) { guildMemberChunkHooks.forEach { it(guildMembers) } }

    /**
     * Registers a lambda to be called when a user is banned.
     *
     * @param block The lambda to call, passing the [GuildBan].
     */
    @DiskordDsl
    fun userBanned(block: suspend (GuildBan) -> Unit) { guildBanHooks += block }
    private val guildBanHooks: MutableList<suspend (GuildBan) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildBanAdd(ban: GuildBan) { guildBanHooks.forEach { it(ban) } }

    /**
     * Registers a lambda to be called when a user is unbanned.
     *
     * @param block The lambda to call, passing the [GuildBan] that was removed.
     */
    @DiskordDsl
    fun userUnbanned(block: suspend (GuildBan) -> Unit) { guildUnbanHooks += block }
    private val guildUnbanHooks: MutableList<suspend (GuildBan) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildBanRemove(ban: GuildBan) { guildUnbanHooks.forEach { it(ban) } }

    /**
     * Registers a lambda to be called when a custom emoji is updated.
     *
     * @param block The lambda to call, passing the [GuildEmojiUpdate].
     */
    @DiskordDsl
    fun guildEmojiUpdated(block: suspend (GuildEmojiUpdate) -> Unit) { guildEmojiUpdateHooks += block }
    private val guildEmojiUpdateHooks: MutableList<suspend (GuildEmojiUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) { guildEmojiUpdateHooks.forEach { it(emojiUpdate) } }

    /**
     * Registers a lambda to be called when a [GuildIntegration] is updated.
     *
     * @param block The lambda to call, passing the [GuildIntegrationUpdate].
     */
    @DiskordDsl
    fun guildIntegrationsUpdated(block: suspend (GuildIntegrationUpdate) -> Unit) { guildIntegrationUpdateHooks += block }
    private val guildIntegrationUpdateHooks: MutableList<suspend (GuildIntegrationUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) { guildIntegrationUpdateHooks.forEach { it(integrationUpdate) } }

    /**
     * Registers a lambda to be called when a user joins a guild.
     *
     * @param block The lambda to call, passing the [GuildMemberAdd].
     */
    @DiskordDsl
    fun userJoinedGuild(block: suspend (GuildMemberAdd) -> Unit) { guildMemberAddHooks += block }
    private val guildMemberAddHooks: MutableList<suspend (GuildMemberAdd) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildMemberAdd(guildMember: GuildMemberAdd) { guildMemberAddHooks.forEach { it(guildMember) } }

    /**
     * Registers a lambda to be called when a guild member is updated.
     *
     * @param block The lambda to call, passing the [GuildMemberUpdate].
     */
    @DiskordDsl
    fun guildMemberUpdated(block: suspend (GuildMemberUpdate) -> Unit) { guildMemberUpdateHooks += block }
    private val guildMemberUpdateHooks: MutableList<suspend (GuildMemberUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) { guildMemberUpdateHooks.forEach { it(guildMemberUpdate) } }

    /**
     * Registers a lambda to be called when a user leaves a guild.
     *
     * @param block The lambda to call, passing the [GuildMemberRemove].
     */
    @DiskordDsl
    fun userLeftGuild(block: suspend (GuildMemberRemove) -> Unit) { guildMemberRemoveHooks += block }
    private val guildMemberRemoveHooks: MutableList<suspend (GuildMemberRemove) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildMemberRemove(guildMemberRemove: GuildMemberRemove) { guildMemberRemoveHooks.forEach { it(guildMemberRemove) } }

    /**
     * Registers a lambda to be called when a [Role] is created.
     *
     * @param block The lambda to call, passing the [GuildRoleCreate].
     */
    @DiskordDsl
    fun roleCreated(block: suspend (GuildRoleCreate) -> Unit) { guildRoleCreatedHooks += block }
    private val guildRoleCreatedHooks: MutableList<suspend (GuildRoleCreate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) { guildRoleCreatedHooks.forEach { it(guildRoleCreate) } }

    /**
     * Registers a lambda to be called when a [Role] is updated.
     *
     * @param block The lambda to call, passing the [GuildRoleUpdate].
     */
    @DiskordDsl
    fun roleUpdated(block: suspend (GuildRoleUpdate) -> Unit) { guildRoleUpdatedHooks += block }
    private val guildRoleUpdatedHooks: MutableList<suspend (GuildRoleUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) { guildRoleUpdatedHooks.forEach { it(guildRoleUpdate) } }

    /**
     * Registers a lambda to be called when a [Role] is deleted.
     *
     * @param block The lambda to call, passing the [GuildRoleDelete].
     */
    @DiskordDsl
    fun roleDeleted(block: suspend (GuildRoleDelete) -> Unit) { guildRoleDeletedHooks += block }
    private val guildRoleDeletedHooks: MutableList<suspend (GuildRoleDelete) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) { guildRoleDeletedHooks.forEach { it(guildRoleDelete) } }

    /**
     * Registers a lambda to be called when a [Message] is created.
     *
     * @param block The lambda to call, passing the [Message].
     */
    @DiskordDsl
    fun messageCreated(block: suspend (Message) -> Unit) { messageCreateHooks += block }
    private val messageCreateHooks: MutableList<suspend (Message) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageCreate(message: Message) { messageCreateHooks.forEach { it(message) } }

    /**
     * Registers a lambda to be called when a [Message] is updated.
     *
     * @param block The lambda to call, passing the [MessageUpdate].
     */
    @DiskordDsl
    fun messageUpdated(block: suspend (MessageUpdate) -> Unit) { messageUpdateHooks += block }
    private val messageUpdateHooks: MutableList<suspend (MessageUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageUpdate(message: MessageUpdate) { messageUpdateHooks.forEach { it(message) } }

    /**
     * Registers a lambda to be called when a [Message] is deleted.
     *
     * @param block The lambda to call, passing the [MessageDelete].
     */
    @DiskordDsl
    fun messageDeleted(block: suspend (MessageDelete) -> Unit) { messageDeleteHooks += block }
    private val messageDeleteHooks: MutableList<suspend (MessageDelete) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageDelete(message: MessageDelete) { messageDeleteHooks.forEach { it(message) } }

    /**
     * Registers a lambda to be called when messages are bulk deleted.
     *
     * @param block The lambda to call, passing the [BulkMessageDelete].
     */
    @DiskordDsl
    fun messagesBulkDeleted(block: suspend (BulkMessageDelete) -> Unit) { messageBulkDeleteHooks += block }
    private val messageBulkDeleteHooks: MutableList<suspend (BulkMessageDelete) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageBulkDelete(message: BulkMessageDelete) { messageBulkDeleteHooks.forEach { it(message) } }

    /**
     * Registers a lambda to be called when a reaction is added to a message.
     *
     * @param block The lambda to call, passing the [MessageReaction].
     */
    @DiskordDsl
    fun reactionAdded(block: suspend (MessageReaction) -> Unit) { messageReactionAddHooks += block }
    private val messageReactionAddHooks: MutableList<suspend (MessageReaction) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageReactionAdd(reactionAdd: MessageReaction) { messageReactionAddHooks.forEach { it(reactionAdd) } }

    /**
     * Registers a lambda to be called when all reactions are removed from a message.
     *
     * @param block The lambda to call, passing the [MessageReactionRemoveAll].
     */
    @DiskordDsl
    fun reactionRemoved(block: suspend (MessageReaction) -> Unit) { messageReactionRemoveHooks += block }
    private val messageReactionRemoveHooks: MutableList<suspend (MessageReaction) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageReactionRemove(reactionRemove: MessageReaction) { messageReactionRemoveHooks.forEach { it(reactionRemove) } }

    /**
     * Registers a lambda to be called when all reactions are removed from a message.
     *
     * @param block The lambda to call, passing the [MessageReactionRemoveAll].
     */
    @DiskordDsl
    fun allReactionsRemoved(block: suspend (MessageReactionRemoveAll) -> Unit) { messageReactionRemoveAllHooks += block }
    private val messageReactionRemoveAllHooks: MutableList<suspend (MessageReactionRemoveAll) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) { messageReactionRemoveAllHooks.forEach { it(messageReactionRemoveAll) } }

    /**
     * Registers a lambda to be called when a user's presence has been updated.
     *
     * @param block The lambda to call, passing the [PresenceUpdate].
     */
    @DiskordDsl
    fun userPresenceUpdated(block: suspend (PresenceUpdate) -> Unit) { presenceUpdateHooks += block }
    private val presenceUpdateHooks: MutableList<suspend (PresenceUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onPresenceUpdate(presenceUpdate: PresenceUpdate) { presenceUpdateHooks.forEach { it(presenceUpdate) } }

    /**
     * Registers a lambda to be called when a user starts typing.
     *
     * @param block The lambda to call, passing the [TypingStart].
     */
    @DiskordDsl
    fun userTyping(block: suspend (TypingStart) -> Unit) { typingStartHooks += block }
    private val typingStartHooks: MutableList<suspend (TypingStart) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onTypingStart(typingStart: TypingStart) { typingStartHooks.forEach { it(typingStart) } }

    /**
     * Registers a lambda to be called when a [User] has been updated.
     *
     * @param block The lambda to call, passing the updated [User].
     */
    @DiskordDsl
    fun userUpdated(block: suspend (User) -> Unit) { userUpdateHooks += block }
    private val userUpdateHooks: MutableList<suspend (User) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onUserUpdate(user: User) { userUpdateHooks.forEach { it(user) } }

    /**
     * Registers a lambda to be called when a user's [VoiceState] has been changed.
     *
     * @param block The lambda to call, passing the new [VoiceState].
     */
    @DiskordDsl
    fun userVoiceStateChanged(block: suspend (VoiceState) -> Unit) { voiceStateUpdateHooks += block }
    private val voiceStateUpdateHooks: MutableList<suspend (VoiceState) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onVoiceStateUpdate(voiceState: VoiceState) { voiceStateUpdateHooks.forEach { it(voiceState) } }

    /**
     * Registers a lambda to be called when a guild's voice server has been updated.
     *
     * @param block The lambda to call, passing the [VoiceServerUpdate].
     */
    @DiskordDsl
    fun voiceServerUpdated(block: suspend (VoiceServerUpdate) -> Unit) { voiceServerUpdateHooks += block }
    private val voiceServerUpdateHooks: MutableList<suspend (VoiceServerUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) { voiceServerUpdateHooks.forEach { it(voiceServerUpdate) } }

    /**
     * Registers a lambda to be called when a [Webhook] has been updated.
     *
     * @param block The lambda to call, passing the [WebhookUpdate].
     */
    @DiskordDsl
    fun webhookUpdated(block: suspend (WebhookUpdate) -> Unit) { webhookUpdatesHooks += block }
    private val webhookUpdatesHooks: MutableList<suspend (WebhookUpdate) -> Unit> = ArrayList()
    /**
     * @suppress Maps event to DSL hooks.
     */
    override suspend fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) { webhookUpdatesHooks.forEach { it(webhookUpdate) } }
}

/**
 * DSL function for initializing a bot.
 *
 * @param token The bot token for the bot user.
 * @param block The DSL lambda to execute.
 * @param shardId The shard id, if this bot is sharded.
 * @param shardCount The count of shards, if the bot is sharded.
 *
 * @return A [Bot] instance using the token and DSL hooks specified in the block.
 */
@DiskordDsl
fun bot(token: String, autoStart: Boolean = true, shardId: Int = 0, shardCount: Int = 0, block: Bot.() -> Unit) = Bot(token, autoStart, shardId, shardCount).apply(block)
