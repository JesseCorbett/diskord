package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.*

val Message.isFromUser: Boolean
    get() = this.author != null && !this.author.isBot

val Message.isFromBot: Boolean
    get() = this.author != null && this.author.isBot

val Message.isFromWebhook: Boolean
    get() = this.webHookId != null

fun String.toUserMention() = "<@$this>"

val User.mention: String
    get() = this.id.toUserMention()

fun String.toRoleMention() = "<@&$this>"

val Role.mention: String
    get() = this.id.toRoleMention()

val Channel.mention: String
    get() = "#$name"

val Emoji.isUnicode: Boolean
    get() = this.id == null

val Emoji.tag: String
    get() = if (isUnicode) name else "<${if (isAnimated) "a" else ""}:$name:$id>"
