package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.*


/**
 * Message extensions
 */
val Message.isFromUser: Boolean
    get() = !this.author.isBot

val Message.isFromBot: Boolean
    get() = this.author.isBot && this.webHookId == null

val Message.isFromWebhook: Boolean
    get() = this.webHookId != null

val Message.authorId: String
    get() = this.author.id


/**
 * User extensions
 */
fun String.toUserMention() = "<@$this>"

val User.mention: String
    get() = this.id.toUserMention()


/**
 * Role extensions
 */
fun String.toRoleMention() = "<@&$this>"

val Role.mention: String
    get() = this.id.toRoleMention()


/**
 * Channel extensions
 */
val Channel.mention: String
    get() = "#$name"


/**
 * Emoji extensions
 */
val Emoji.isUnicode: Boolean
    get() = this.id == null

val Emoji.tag: String
    get() = if (isUnicode) name else "<${if (isAnimated) "a" else ""}:$name:$id>"
