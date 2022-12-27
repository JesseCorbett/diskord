package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.common.Embed
import com.jessecorbett.diskord.api.interaction.ApplicationCommand
import com.jessecorbett.diskord.api.interaction.callback.ChannelMessageWithSource
import com.jessecorbett.diskord.api.interaction.callback.DeferredChannelMessageWithSource
import com.jessecorbett.diskord.api.interaction.callback.InteractionCommandCallbackDataFlags
import com.jessecorbett.diskord.api.interaction.callback.InteractionResponse
import com.jessecorbett.diskord.bot.BotContext

public class ResponseContext internal constructor(private val context: BotContext) : BotContext by context {
    private var hasResponded = false

    /**
     * Acknowledges the interaction and informs the user and Discord that a response will be coming shortly
     */
    public suspend fun ApplicationCommand.acknowledgeForFutureResponse() {
        respond(DeferredChannelMessageWithSource())
    }

    /**
     * Responds to the interaction with a channel message
     */
    public suspend fun ApplicationCommand.respond(builder: suspend ResponseBuilder.() -> Unit) {
        val response = ResponseBuilder().apply {
            builder()
        }.build()

        respond(response)
    }

    private suspend fun ApplicationCommand.respond(response: InteractionResponse) {
        if (hasResponded) {
            client.deleteOriginalInteractionResponse()
        }

        hasResponded = true
        client.createInteractionResponse(id, response)
    }

    public class ResponseBuilder {
        public var content: String? = null
        public var tts: Boolean = false
        public var embeds: MutableList<Embed> = mutableListOf()
        public var allowedMentions: AllowedMentions? = null
        public var flags: InteractionCommandCallbackDataFlags = InteractionCommandCallbackDataFlags.NONE
        // TODO: components
        public var attachments: MutableList<Attachment> = mutableListOf()

        /**
         * Marks the message as ephemeral, so only the invoking user can see it
         */
        public val ephemeral: Boolean
            get() {
                flags += InteractionCommandCallbackDataFlags.EPHEMERAL
                return true
            }

        /**
         * Prevents embeds from occurring, such as when a hyperlink may be present in the [content]
         */
        public val noEmbeds: Boolean
            get() {
                flags += InteractionCommandCallbackDataFlags.SUPPRESS_EMBEDS
                return true
            }

        internal fun build(): ChannelMessageWithSource = ChannelMessageWithSource(
            data = ChannelMessageWithSource.Data(
                tts = tts,
                content = content,
                embeds = embeds,
                allowedMentions = allowedMentions,
                flags = flags,
                components = emptyList(),
                attachments = attachments
            )
        )
    }
}
