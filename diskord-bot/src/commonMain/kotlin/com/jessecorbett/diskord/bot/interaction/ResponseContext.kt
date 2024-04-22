package com.jessecorbett.diskord.bot.interaction

import com.jessecorbett.diskord.api.channel.AllowedMentions
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.api.common.ActionRow
import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.common.TextInput
import com.jessecorbett.diskord.api.interaction.CommandInteractionDataResolved
import com.jessecorbett.diskord.api.interaction.CommandInteractionOptionResponse
import com.jessecorbett.diskord.api.interaction.Interaction
import com.jessecorbett.diskord.api.interaction.ModalSubmit
import com.jessecorbett.diskord.api.interaction.callback.*
import com.jessecorbett.diskord.api.webhook.CreateWebhookMessage
import com.jessecorbett.diskord.bot.BotContext

/**
 * The action scope of the response to an [Interaction].
 *
 * @property command The instance of the command the interaction is for
 * @property options The parameters passed from the Discord user via the command
 * @property data The resolved data relative to the options
 */
@InteractionModule
public data class ResponseContext<I: Interaction> internal constructor(
    private val context: BotContext,
    public val command: I,
    public val options: List<CommandInteractionOptionResponse>,
    public val data: CommandInteractionDataResolved?,
    private val registerModal: (String, suspend ResponseContext<ModalSubmit>.(ModalSubmit) -> Unit) -> Unit
) : BotContext by context {
    private var hasResponded = false
    private var hasAckedForFuture = false

    /**
     * Acknowledges the interaction and informs the user and Discord that a response will be coming shortly
     */
    public suspend fun acknowledgeForFutureResponse(ephemeral: Boolean = false) {
        hasAckedForFuture = true
        respond(DeferredChannelMessageWithSource(DeferredChannelMessageWithSource.Data(
            if (ephemeral) InteractionCommandCallbackDataFlags.EPHEMERAL else InteractionCommandCallbackDataFlags.NONE
        )))
    }

    /**
     * Responds to the interaction with a channel message
     */
    public suspend fun respond(builder: suspend ResponseBuilder.() -> Unit) {
        val response = ResponseBuilder().apply {
            builder()
        }.build()

        respond(response)
    }

    /**
     * Deletes the original interaction response
     */
    public suspend fun Interaction.deleteOriginalResponse() {
        client.deleteOriginalInteractionResponse()
    }

    /**
     * Respond to the interaction by creating a modal for the user
     */
    @InteractionModule
    public suspend fun Interaction.createModal(
        title: String,
        vararg inputs: TextInput,
        callback: suspend ResponseContext<ModalSubmit>.(ModalSubmit) -> Unit
    ) {
        val response = CreateModalResult(CreateModalResult.Data(
            customId = id,
            modalTitle = title,
            components = inputs.map { ActionRow(it) }
        ))
        respond(response)

        registerModal(id, callback)
    }

    private suspend fun respond(response: InteractionResponse) {
        if (hasAckedForFuture && response is ChannelMessageWithSource) {
            context.webhook(command.applicationId).execute(
                command.token,
                CreateWebhookMessage(
                    useTTS = response.data.tts,
                    content = response.data.content,
                    embeds = response.data.embeds,
                    allowedMentions = response.data.allowedMentions ?: AllowedMentions.NONE,
                    flags = response.data.flags,
                    components = response.data.components
                ),
                true
            )
            return
        }


        if (hasResponded) {
            command.client.deleteOriginalInteractionResponse()
        }

        hasResponded = true
        command.client.createInteractionResponse(command.id, response)
    }

    /**
     * Convenience class for building a message response
     *
     * TODO: Merge into one convenience class for all message building
     */
    public class ResponseBuilder {
        public var content: String? = null
        public var tts: Boolean = false
        public var embeds: List<Embed> = mutableListOf()
        public var allowedMentions: AllowedMentions? = null
        public var flags: InteractionCommandCallbackDataFlags = InteractionCommandCallbackDataFlags.NONE
        // TODO: components
        public var attachments: List<Attachment> = mutableListOf()
        public var components: List<ActionRow> = mutableListOf()

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
                components = components,
                attachments = attachments
            )
        )
    }
}
