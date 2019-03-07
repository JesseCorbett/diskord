package com.jessecorbett.diskord.dsl

/**
 * Utility function to block the current thread while this bot is active
 */
fun Bot.block() {
    while (true) {
        Thread.sleep(1000)
        if (!this.active) {
            // A secondary longer wait and check in case we active check in the middle of a reconnect
            Thread.sleep(5000)
            if (!this.active) {
                break
            }
        }
    }
}
