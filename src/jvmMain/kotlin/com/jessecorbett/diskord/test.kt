package com.jessecorbett.diskord

import com.jessecorbett.diskord.dsl.bot
import com.jessecorbett.diskord.dsl.command
import com.jessecorbett.diskord.dsl.commands

fun main() {
    bot("MzQxNzg5NTQwMTY1MzUzNDgy.DGGL1A.bWBHATsntR7xkQQ3ImWUbMU06UI") {
        commands {
            command("test") {
                reply("test")
                delete()
            }
        }
    }

    while(true) {

    }
}
