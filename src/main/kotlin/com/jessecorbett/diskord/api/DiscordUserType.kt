package com.jessecorbett.diskord.api

/**
 * The two types of users.
 *
 * @property type The auth token type label.
 */
enum class DiscordUserType(val type: String) {
    /**
     * Bots
     */
    BOT("Bot"),
    /**
     * Users
     */
    BEARER("Bearer");

    override fun toString() = type
}
