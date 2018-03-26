package com.jessecorbett.diskord.internal

data class DiscordToken(val token: String, val tokenType: TokenType)

enum class TokenType(val type: String) {
    BOT("Bot"),
    BEARER("Bearer")
}
