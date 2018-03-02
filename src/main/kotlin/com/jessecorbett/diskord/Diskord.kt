package com.jessecorbett.diskord

private const val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DW8y7Q.jgJgT3EBRyAzi7CeoEt8-CXPBhA"

fun main(args: Array<String>) {
    val discordSocket = WebSocketConnection(token, eventListener = Listener())
    val discordClient = RestClient(token)

    while (true) {

    }
}

class Listener : EventListener()

// This class is basically just an in-progress testing resource, will eventually be removed as testing is no longer necessary
