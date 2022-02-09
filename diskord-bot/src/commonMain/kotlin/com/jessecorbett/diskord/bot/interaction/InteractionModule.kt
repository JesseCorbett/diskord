package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.interaction.*
import com.jessecorbett.diskord.api.interaction.callback.InteractionCallbackType
import com.jessecorbett.diskord.api.interaction.callback.InteractionResponse
import com.jessecorbett.diskord.bot.BotBase

@DslMarker
public annotation class InteractionModule

/**
 * Installs the interactions DSL and provides a scope for defining commands
 */
@InteractionModule
public fun BotBase.interactions(commands: InteractionBuilder.() -> Unit) {
    registerModule { dispatcher, context ->
        val user = context.global().getUser()

        // Handle pings automatically
        dispatcher.onInteractionCreate { interaction ->
            if (interaction is InteractionPing) {
                context.interaction(user.id, interaction.token).createInteractionResponse(
                    interactionId = interaction.id,
                    interactionResponse = InteractionResponse(InteractionCallbackType.Pong, null)
                )
            }
        }

        InteractionBuilder(user.id, dispatcher, context).commands()
    }
}
