package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.events.*
import com.jessecorbett.diskord.api.gateway.Channel
import com.jessecorbett.diskord.api.gateway.commands.Resume
import com.jessecorbett.diskord.api.models.*

abstract class EventListener {
    fun onEvent(event: DiscordEvent) {

    }

    fun onReady(ready: Ready) {

    }

    fun onResumed(resume: Resume) {

    }

    fun onChannelCreate(channel: Channel) {

    }

    fun onChannelUpdate(channel: Channel) {

    }

    fun onChannelDelete(channel: Channel) {

    }

    fun onChannelPinsUpdate(channelPinUpdate: ChannelPinUpdate) {

    }

    fun onGuildCreate(guild: CreatedGuild) {

    }

    fun onGuildUpdate(guild: Guild) {

    }

    fun onGuildDelete(guild: Guild) {

    }

    fun onGuildBanAdd(ban: GuildBan) {

    }

    fun onGuildBanRemove(ban: GuildBan) {

    }

    fun onGuildEmojiUpdate(emojiUpdate: GuildEmojiUpdate) {

    }

    fun onGuildIntegrationsUpdate(integrationUpdate: GuildIntegrationUpdate) {

    }

    fun onGuildMemberAdd(guildMember: GuildMemberAdd) {

    }

    fun onGuildMemberUpdate(guildMemberUpdate: GuildMemberUpdate) {

    }

    fun onGuildMemberRemove(guildMemeberRemove: GuildMemeberRemove) {

    }

    fun onGuildMemberChunk(guildMembers: GuildMembersChunk) {

    }

    fun onGuildRoleCreate(guildRoleCreate: GuildRoleCreate) {

    }

    fun onGuildRoleUpdate(guildRoleUpdate: GuildRoleUpdate) {

    }

    fun onGuildRoleDelete(guildRoleDelete: GuildRoleDelete) {

    }

    fun onMessageCreate(message: Message) {
        if (message.author == null) {
            println()
            println("HERE!!!!!!!!!!!!!!!!!")
            println(message)
            println()
        }

        if (message.content == null) {
            println()
            println("HERE!!!!!!!!!!!!!!!!!")
            println(message)
            println()
        }

        if (message.sentAt == null) {
            println()
            println("HERE!!!!!!!!!!!!!!!!!")
            println(message)
            println()
        }

        if (message.type == null) {
            println()
            println("HERE!!!!!!!!!!!!!!!!!")
            println(message)
            println()
        }
    }

    fun onMessageUpdate(message: Message) {

    }

    fun onMessageDelete(message: MessageDelete) {

    }

    fun onMessageBulkDelete(message: BulkMessageDelete) {

    }

    fun onMessageReactionAdd(reactionAdd: MessageReaction) {

    }

    fun onMessageReactionRemove(reactionRemove: MessageReaction) {

    }

    fun onMessageReactionRemoveAll(messageReactionRemoveAll: MessageReactionRemoveAll) {

    }

    fun onPresenceUpdate(presenceUpdate: PresenceUpdate) {

    }

    fun onTypingStart(typingStart: TypingStart) {

    }

    fun onUserUpdate(user: User) {

    }

    fun onVoiceStateUpdate(voiceState: VoiceState) {

    }

    fun onVoiceServerUpdate(voiceServerUpdate: VoiceServerUpdate) {

    }

    fun onWebhooksUpdate(webhookUpdate: WebhookUpdate) {

    }
}