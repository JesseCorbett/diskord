package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.model.Channel
import com.jessecorbett.diskord.api.model.Guild
import com.jessecorbett.diskord.api.model.Message
import com.jessecorbett.diskord.api.model.User
import com.jessecorbett.diskord.api.websocket.events.*
import com.jessecorbett.diskord.api.model.BulkMessageDelete
import com.jessecorbett.diskord.api.model.MessageDelete
import com.jessecorbett.diskord.api.model.VoiceState
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

abstract class EventListener(val context: CoroutineContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
    open suspend fun onEvent(event: DiscordEvent) {

    }

    open suspend fun onReady(ready: Ready) {

    }

    open suspend fun onResumed(resumed: Resumed) {

    }

    open suspend fun onChannelCreate(channel: Channel) {

    }

    open suspend fun onChannelUpdate(channel: Channel) {

    }

    open suspend fun onChannelDelete(channel: Channel) {

    }

    open suspend fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate) {

    }

    open suspend fun onGuildCreate(guild: CreatedGuild) {

    }

    open suspend fun onGuildUpdate(guild: Guild) {

    }

    open suspend fun onGuildDelete(guild: Guild) {

    }

    open suspend fun onGuildBanAdd(ban: GuildBan) {

    }

    open suspend fun onGuildBanRemove(ban: GuildBan) {

    }

    open suspend fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) {

    }

    open suspend fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) {

    }

    open suspend fun onGuildMemberAdd(guildMember: GuildMemberAdd) {

    }

    open suspend fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) {

    }

    open suspend fun onGuildMemberRemove(guildMemberRemove: GuildMemeberRemove) {

    }

    open suspend fun onGuildMemberChunk(guildMembers: GuildMembersChunk) {

    }

    open suspend fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) {

    }

    open suspend fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) {

    }

    open suspend fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) {

    }

    open suspend fun onMessageCreate(message: Message) {

    }

    open suspend fun onMessageUpdate(message: MessageUpdate) {

    }

    open suspend fun onMessageDelete(message: MessageDelete) {

    }

    open suspend fun onMessageBulkDelete(message: BulkMessageDelete) {

    }

    open suspend fun onMessageReactionAdd(reactionAdd: MessageReaction) {

    }

    open suspend fun onMessageReactionRemove(reactionRemove: MessageReaction) {

    }

    open suspend fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) {

    }

    open suspend fun onPresenceUpdate(presenceUpdate: PresenceUpdate) {

    }

    open suspend fun onTypingStart(typingStart: TypingStart) {

    }

    open suspend fun onUserUpdate(user: User) {

    }

    open suspend fun onVoiceStateUpdate(voiceState: VoiceState) {

    }

    open suspend fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) {

    }

    open suspend fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) {

    }
}
