package com.jessecorbett.diskord.api

/**
 * The two types of users.
 *
 * @property type The auth token type label.
 */
public enum class DiscordUserType(public val type: String) {
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
    override fun toString(): String = type
}
