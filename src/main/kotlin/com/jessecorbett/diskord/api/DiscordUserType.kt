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

    /**
     * Over the wire we want to send the type string, not enum name.
     */
    override fun toString() = type
}
