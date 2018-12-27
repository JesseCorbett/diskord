package com.jessecorbett.diskord.api

enum class DiscordUserType(val type: String) {
    BOT("Bot"),
    BEARER("Bearer");

    override fun toString() = type
}
