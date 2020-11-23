package com.jessecorbett.diskord

import com.jessecorbett.diskord.api.DiscordUserType
import com.jessecorbett.diskord.api.rest.client.DiscordClient
import com.jessecorbett.diskord.api.rest.client.internal.DefaultRestClient
import com.jessecorbett.diskord.util.DiskordInternals


@OptIn(DiskordInternals::class)
suspend fun main() {
    val client = DefaultRestClient(DiscordUserType.BOT, "MzQxNzg5NTQwMTY1MzUzNDgy.DGGL1A.bWBHATsntR7xkQQ3ImWUbMU06UI", "test", "test")
    val discord = DiscordClient(client)

    println(discord.getUser())
}
