package com.jessecorbett.diskord.example

import com.jessecorbett.diskord.dsl.*
import com.jessecorbett.diskord.util.words

suspend fun main() {
    bot("your-bot-token") {
        commands(".") { // "." is the default, but is provided here anyway for example purposes
            command("echo") {
                this reply this.words.joinToString(" ")
            }

            // Like echo, but deletes the command message
            command("say") {
                this replyAndDelete  this.words.joinToString(" ")
            }

            command("cat") {
                delete()
                reply {
                    text = "What a cute cat!"
                    image("http://placekitten.com/200/300") {
                        imageWidth = 200
                        imageHeight = 300
                    }
                }
            }
        }
    }
}
