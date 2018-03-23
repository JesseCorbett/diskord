package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.Channel
import com.jessecorbett.diskord.api.Guild
import com.jessecorbett.diskord.api.Message
import com.jessecorbett.diskord.api.User
import com.jessecorbett.diskord.api.gateway.events.*
import com.jessecorbett.diskord.api.models.BulkMessageDelete
import com.jessecorbett.diskord.api.models.MessageDelete
import com.jessecorbett.diskord.api.models.VoiceState

abstract class EventListener {
    open fun onEvent(event: DiscordEvent) {

    }

    open fun onReady(ready: Ready) {

    }

    open fun onResumed(resumed: Resumed) {

    }

    open fun onChannelCreate(channel: Channel) {

    }

    open fun onChannelUpdate(channel: Channel) {

    }

    open fun onChannelDelete(channel: Channel) {

    }

    open fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate) {

    }

    open fun onGuildCreate(guild: CreatedGuild) {

    }

    open fun onGuildUpdate(guild: Guild) {

    }

    open fun onGuildDelete(guild: Guild) {

    }

    open fun onGuildBanAdd(ban: GuildBan) {

    }

    open fun onGuildBanRemove(ban: GuildBan) {

    }

    open fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) {

    }

    open fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) {

    }

    open fun onGuildMemberAdd(guildMember: GuildMemberAdd) {

    }

    open fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) {

    }

    open fun onGuildMemberRemove(guildMemeberRemove: GuildMemeberRemove) {

    }

    open fun onGuildMemberChunk(guildMembers: GuildMembersChunk) {

    }

    open fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) {

    }

    open fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) {

    }

    open fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) {

    }

    open fun onMessageCreate(message: Message) {

    }

    open fun onMessageUpdate(message: MessageUpdate) {

    }

    open fun onMessageDelete(message: MessageDelete) {

    }

    open fun onMessageBulkDelete(message: BulkMessageDelete) {

    }

    open fun onMessageReactionAdd(reactionAdd: MessageReaction) {

    }

    open fun onMessageReactionRemove(reactionRemove: MessageReaction) {

    }

    open fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) {

    }

    open fun onPresenceUpdate(presenceUpdate: PresenceUpdate) {

    }

    open fun onTypingStart(typingStart: TypingStart) {

    }

    open fun onUserUpdate(user: User) {

    }

    open fun onVoiceStateUpdate(voiceState: VoiceState) {

    }

    open fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) {

    }

    open fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) {

    }
}