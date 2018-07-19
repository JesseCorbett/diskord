package com.jessecorbett.diskord.internal

enum class DiscordUserType(val type: String) {
    BOT("Bot"),
    BEARER("Bearer");

    override fun toString() = type
}
