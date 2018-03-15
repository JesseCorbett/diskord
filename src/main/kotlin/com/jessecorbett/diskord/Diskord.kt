package com.jessecorbett.diskord

private const val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DYuHdA.wVgVrSJ0DqO0RfUHwm9xeZStPNY"

fun main(args: Array<String>) {
    val discordClient = GuildClient(token, "219226526929911809")
    discordClient.getMembers(10).map { it.user.username }.forEach(::println)
}
