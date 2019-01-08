package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.model.*
import com.jessecorbett.diskord.api.websocket.DiscordWebSocket
import com.jessecorbett.diskord.api.websocket.EventListener
import com.jessecorbett.diskord.api.websocket.events.*
import com.jessecorbett.diskord.util.ClientStore
import com.jessecorbett.diskord.util.sendMessage

@DslMarker
annotation class DiskordDsl

class Bot(token: String) : EventListener() {
    private val websocket = DiscordWebSocket(token, this)
    val clientStore = ClientStore(token)

    fun shutdown(forceClose: Boolean = false) = websocket.close(forceClose)

    fun restart() = websocket.restart()

    suspend fun Message.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun Message.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    suspend fun MessageUpdate.reply(text: String) = clientStore.channels[this.channelId].sendMessage(text)
    suspend fun MessageUpdate.delete() = clientStore.channels[this.channelId].deleteMessage(this.id)

    @DiskordDsl
    fun anyEvent(block: suspend (DiscordEvent, String) -> Unit) { anyEventHooks += block }
    private val anyEventHooks: MutableList<suspend (DiscordEvent, String) -> Unit> = ArrayList()
    override suspend fun onEvent(event: DiscordEvent, json: String) { anyEventHooks.forEach { it(event, json) } }

    @DiskordDsl
    fun started(block: suspend (Ready) -> Unit) { readyHooks += block }
    private val readyHooks: MutableList<suspend (Ready) -> Unit> = ArrayList()
    override suspend fun onReady(ready: Ready) { readyHooks.forEach { it(ready) } }

    @DiskordDsl
    fun resumed(block: suspend (Resumed) -> Unit) { resumedHooks += block }
    private val resumedHooks: MutableList<suspend (Resumed) -> Unit> = ArrayList()
    override suspend fun onResumed(resumed: Resumed) { resumedHooks.forEach { it(resumed) } }

    @DiskordDsl
    fun channelCreated(block: suspend (Channel) -> Unit) { channelCreateHooks += block }
    private val channelCreateHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    override suspend fun onChannelCreate(channel: Channel) { channelCreateHooks.forEach { it(channel) } }

    @DiskordDsl
    fun channelUpdated(block: suspend (Channel) -> Unit) { channelUpdateHooks += block }
    private val channelUpdateHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    override suspend fun onChannelUpdate(channel: Channel) { channelUpdateHooks.forEach { it(channel) } }

    @DiskordDsl
    fun channelDeleted(block: suspend (Channel) -> Unit) { channelDeleteHooks += block }
    private val channelDeleteHooks: MutableList<suspend (Channel) -> Unit> = ArrayList()
    override suspend fun onChannelDelete(channel: Channel) { channelDeleteHooks.forEach { it(channel) } }

    @DiskordDsl
    fun pinsUpdated(block: suspend (ChannelPinUpdate) -> Unit) { channelPinsUpdateHooks += block }
    private val channelPinsUpdateHooks: MutableList<suspend (ChannelPinUpdate) -> Unit> = ArrayList()
    override suspend fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate)  { channelPinsUpdateHooks.forEach { it(channelPinUpdate) } }

    // TODO: Cache this perhaps, should we want to store this
    @DiskordDsl
    fun guildCreated(block: suspend (CreatedGuild) -> Unit) { guildCreateHooks += block }
    private val guildCreateHooks: MutableList<suspend (CreatedGuild) -> Unit> = ArrayList()
    override suspend fun onGuildCreate(guild: CreatedGuild) { guildCreateHooks.forEach { it(guild) } }

    @DiskordDsl
    fun guildUpdated(block: suspend (Guild) -> Unit) { guildUpdateHooks += block }
    private val guildUpdateHooks: MutableList<suspend (Guild) -> Unit> = ArrayList()
    override suspend fun onGuildUpdate(guild: Guild) { guildUpdateHooks.forEach { it(guild) } }

    @DiskordDsl
    fun guildDeleted(block: suspend (Guild) -> Unit) { guildDeleteHooks += block }
    private val guildDeleteHooks: MutableList<suspend (Guild) -> Unit> = ArrayList()
    override suspend fun onGuildDelete(guild: Guild) { guildDeleteHooks.forEach { it(guild) } }

    // TODO: Cache this perhaps, should we want to store this
    @DiskordDsl
    fun guildMemberChunkReceived(block: suspend (GuildMembersChunk) -> Unit) { guildMemberChunkHooks += block }
    private val guildMemberChunkHooks: MutableList<suspend (GuildMembersChunk) -> Unit> = ArrayList()
    override suspend fun onGuildMemberChunk(guildMembers: GuildMembersChunk) { guildMemberChunkHooks.forEach { it(guildMembers) } }

    @DiskordDsl
    fun userBanned(block: suspend (GuildBan) -> Unit) { guildBanHooks += block }
    private val guildBanHooks: MutableList<suspend (GuildBan) -> Unit> = ArrayList()
    override suspend fun onGuildBanAdd(ban: GuildBan) { guildBanHooks.forEach { it(ban) } }

    @DiskordDsl
    fun userUnbanned(block: suspend (GuildBan) -> Unit) { guildUnbanHooks += block }
    private val guildUnbanHooks: MutableList<suspend (GuildBan) -> Unit> = ArrayList()
    override suspend fun onGuildBanRemove(ban: GuildBan) { guildUnbanHooks.forEach { it(ban) } }

    @DiskordDsl
    fun guildEmojiUpdated(block: suspend (GuildEmojiUpdate) -> Unit) { guildEmojiUpdateHooks += block }
    private val guildEmojiUpdateHooks: MutableList<suspend (GuildEmojiUpdate) -> Unit> = ArrayList()
    override suspend fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) { guildEmojiUpdateHooks.forEach { it(emojiUpdate) } }

    @DiskordDsl
    fun guildIntegrationsUpdated(block: suspend (GuildIntegrationUpdate) -> Unit) { guildIntegrationUpdateHooks += block }
    private val guildIntegrationUpdateHooks: MutableList<suspend (GuildIntegrationUpdate) -> Unit> = ArrayList()
    override suspend fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) { guildIntegrationUpdateHooks.forEach { it(integrationUpdate) } }

    @DiskordDsl
    fun userJoinedGuild(block: suspend (GuildMemberAdd) -> Unit) { guildMemberAddHooks += block }
    private val guildMemberAddHooks: MutableList<suspend (GuildMemberAdd) -> Unit> = ArrayList()
    override suspend fun onGuildMemberAdd(guildMember: GuildMemberAdd) { guildMemberAddHooks.forEach { it(guildMember) } }

    @DiskordDsl
    fun guildMemberUpdated(block: suspend (GuildMemberUpdate) -> Unit) { guildMemberUpdateHooks += block }
    private val guildMemberUpdateHooks: MutableList<suspend (GuildMemberUpdate) -> Unit> = ArrayList()
    override suspend fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) { guildMemberUpdateHooks.forEach { it(guildMemberUpdate) } }

    @DiskordDsl
    fun userLeftGuild(block: suspend (GuildMemeberRemove) -> Unit) { guildMemberRemoveHooks += block }
    private val guildMemberRemoveHooks: MutableList<suspend (GuildMemeberRemove) -> Unit> = ArrayList()
    override suspend fun onGuildMemberRemove(guildMemberRemove: GuildMemeberRemove) { guildMemberRemoveHooks.forEach { it(guildMemberRemove) } }

    @DiskordDsl
    fun roleCreated(block: suspend (GuildRoleCreate) -> Unit) { guildRoleCreatedHooks += block }
    private val guildRoleCreatedHooks: MutableList<suspend (GuildRoleCreate) -> Unit> = ArrayList()
    override suspend fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) { guildRoleCreatedHooks.forEach { it(guildRoleCreate) } }

    @DiskordDsl
    fun roleUpdated(block: suspend (GuildRoleUpdate) -> Unit) { guildRoleUpdatedHooks += block }
    private val guildRoleUpdatedHooks: MutableList<suspend (GuildRoleUpdate) -> Unit> = ArrayList()
    override suspend fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) { guildRoleUpdatedHooks.forEach { it(guildRoleUpdate) } }

    @DiskordDsl
    fun roleDeleted(block: suspend (GuildRoleDelete) -> Unit) { guildRoleDeletedHooks += block }
    private val guildRoleDeletedHooks: MutableList<suspend (GuildRoleDelete) -> Unit> = ArrayList()
    override suspend fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) { guildRoleDeletedHooks.forEach { it(guildRoleDelete) } }

    @DiskordDsl
    fun messageCreated(block: suspend (Message) -> Unit) { messageCreateHooks += block }
    private val messageCreateHooks: MutableList<suspend (Message) -> Unit> = ArrayList()
    override suspend fun onMessageCreate(message: Message) { messageCreateHooks.forEach { it(message) } }

    @DiskordDsl
    fun messageUpdated(block: suspend (MessageUpdate) -> Unit) { messageUpdateHooks += block }
    private val messageUpdateHooks: MutableList<suspend (MessageUpdate) -> Unit> = ArrayList()
    override suspend fun onMessageUpdate(message: MessageUpdate) { messageUpdateHooks.forEach { it(message) } }

    @DiskordDsl
    fun messageDeleted(block: suspend (MessageDelete) -> Unit) { messageDeleteHooks += block }
    private val messageDeleteHooks: MutableList<suspend (MessageDelete) -> Unit> = ArrayList()
    override suspend fun onMessageDelete(message: MessageDelete) { messageDeleteHooks.forEach { it(message) } }

    @DiskordDsl
    fun messagesBulkDeleted(block: suspend (BulkMessageDelete) -> Unit) { messageBulkDeleteHooks += block }
    private val messageBulkDeleteHooks: MutableList<suspend (BulkMessageDelete) -> Unit> = ArrayList()
    override suspend fun onMessageBulkDelete(message: BulkMessageDelete) { messageBulkDeleteHooks.forEach { it(message) } }

    @DiskordDsl
    fun reactionAdded(block: suspend (MessageReaction) -> Unit) { messageReactionAddHooks += block }
    private val messageReactionAddHooks: MutableList<suspend (MessageReaction) -> Unit> = ArrayList()
    override suspend fun onMessageReactionAdd(reactionAdd: MessageReaction) { messageReactionAddHooks.forEach { it(reactionAdd) } }

    @DiskordDsl
    fun reactionRemoved(block: suspend (MessageReaction) -> Unit) { messageReactionRemoveHooks += block }
    private val messageReactionRemoveHooks: MutableList<suspend (MessageReaction) -> Unit> = ArrayList()
    override suspend fun onMessageReactionRemove(reactionRemove: MessageReaction) { messageReactionRemoveHooks.forEach { it(reactionRemove) } }

    @DiskordDsl
    fun allReactionsRemoved(block: suspend (MessageReactionRemoveAll) -> Unit) { messageReactionRemoveAllHooks += block }
    private val messageReactionRemoveAllHooks: MutableList<suspend (MessageReactionRemoveAll) -> Unit> = ArrayList()
    override suspend fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) { messageReactionRemoveAllHooks.forEach { it(messageReactionRemoveAll) } }

    @DiskordDsl
    fun userPresenceUpdated(block: suspend (PresenceUpdate) -> Unit) { presenceUpdateHooks += block }
    private val presenceUpdateHooks: MutableList<suspend (PresenceUpdate) -> Unit> = ArrayList()
    override suspend fun onPresenceUpdate(presenceUpdate: PresenceUpdate) { presenceUpdateHooks.forEach { it(presenceUpdate) } }

    @DiskordDsl
    fun userTyping(block: suspend (TypingStart) -> Unit) { typingStartHooks += block }
    private val typingStartHooks: MutableList<suspend (TypingStart) -> Unit> = ArrayList()
    override suspend fun onTypingStart(typingStart: TypingStart) { typingStartHooks.forEach { it(typingStart) } }

    @DiskordDsl
    fun userUpdated(block: suspend (User) -> Unit) { userUpdateHooks += block }
    private val userUpdateHooks: MutableList<suspend (User) -> Unit> = ArrayList()
    override suspend fun onUserUpdate(user: User) { userUpdateHooks.forEach { it(user) } }

    @DiskordDsl
    fun userVoiceStateChanged(block: suspend (VoiceState) -> Unit) { voiceStateUpdateHooks += block }
    private val voiceStateUpdateHooks: MutableList<suspend (VoiceState) -> Unit> = ArrayList()
    override suspend fun onVoiceStateUpdate(voiceState: VoiceState) { voiceStateUpdateHooks.forEach { it(voiceState) } }

    @DiskordDsl
    fun voiceServerUpdated(block: suspend (VoiceServerUpdate) -> Unit) { voiceServerUpdateHooks += block }
    private val voiceServerUpdateHooks: MutableList<suspend (VoiceServerUpdate) -> Unit> = ArrayList()
    override suspend fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) { voiceServerUpdateHooks.forEach { it(voiceServerUpdate) } }

    @DiskordDsl
    fun webhookUpdated(block: suspend (WebhookUpdate) -> Unit) { webhookUpdatesHooks += block }
    private val webhookUpdatesHooks: MutableList<suspend (WebhookUpdate) -> Unit> = ArrayList()
    override suspend fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) { webhookUpdatesHooks.forEach { it(webhookUpdate) } }
}

@DiskordDsl
fun bot(token: String, block: Bot.() -> Unit) = Bot(token).apply(block)
