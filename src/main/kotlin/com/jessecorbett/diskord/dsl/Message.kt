package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.rest.*
import com.jessecorbett.diskord.util.Color
import java.time.Instant

/**
 * DSL command to create a message embed.
 *
 * @param block The DSL lambda in which to run the builds DSL commands.
 *
 * @return An [Embed] for use in sending messages.
 */
@DiskordDsl
fun embed(block: Embed.() -> Unit) = Embed().apply(block)

/**
 * DSL command to add an image to an embed.
 *
 * @param url The url of the image.
 * @param block The optional DSL lambda in which to specify properties of the image.
 */
@DiskordDsl
fun Embed.image(url: String, block: EmbedImage.() -> Unit = {}) {
    this.image = EmbedImage(url).apply(block)
}

/**
 * DSL command to add a video to an embed.
 *
 * @param url The url of the video.
 * @param block The optional DSL lambda in which to specify properties of the image.
 */
@DiskordDsl
fun Embed.video(url: String, block: EmbedVideo.() -> Unit = {}) {
    video = EmbedVideo(url).apply(block)
}

/**
 * DSL command to add a provider field to an embed.
 *
 * @param name A provider of the embed.
 * @param url The url for the provider.
 */
@DiskordDsl
fun Embed.provider(name: String, url: String) {
    provider = EmbedProvider(name, url)
}

/**
 * DSL command to add an author field to an embed.
 *
 * @param name The author of the embed.
 * @param block The optional DSL lambda in which to specify properties of the author.
 */
@DiskordDsl
fun Embed.author(name: String, block: EmbedAuthor.() -> Unit = {}) {
    author = EmbedAuthor(name).apply(block)
}

/**
 * DSL command to add a footer to an embed.
 *
 * @param text The text of the footer.
 * @param block The optional DSL lambda in which to specify properties of the footer.
 */
@DiskordDsl
fun Embed.footer(text: String, block: EmbedFooter.() -> Unit = {}) {
    footer = EmbedFooter(text).apply(block)
}

/**
 * DSL command to add a field to a set of fields in an embed.
 *
 * @param name The name of the field.
 * @param value The value of the field.
 * @param inline Should this field be displayed inline.
 */
@DiskordDsl
fun Embed.field(name: String, value: String, inline: Boolean) = fields.add(EmbedField(name, value, inline))


/*
 * Utilities for DSL usage beyond constructing simple discord primitives
 */


/**
 * A combined message and embed class for building messages wholly via DSL.
 *
 * @property message The string content of the message.
 */
class CombinedMessageEmbed(
        var text: String = "",
        var title: String? = null,
        var description: String? = null,
        var url: String? = null,
        var timestamp: Instant? = null,
        var color: Color? = null,
        var footer: EmbedFooter? = null,
        var image: EmbedImage? = null,
        var thumbnail: EmbedImage? = null,
        var video: EmbedVideo? = null,
        var provider: EmbedProvider? = null,
        var author: EmbedAuthor? = null,
        var fields: MutableList<EmbedField> = ArrayList(),
        var type: String = "rich"
) {
    /**
     * The [Embed] object represented.
     *
     * @return The embed. Null if no embed properties have been specified.
     */
    fun embed(): Embed? {
        if (listOf(title, description, url, timestamp, color, footer, image, thumbnail, video, provider, author).all { it == null } && fields.isEmpty()) {
            return null
        }

        return Embed(title, description, url, timestamp, color, footer, image, thumbnail, video, provider, author, fields, type)
    }
}

/**
 * DSL command to create a message.
 *
 * @param block The DSL lambda in which to run the builds DSL commands.
 *
 * @return A [CombinedMessageEmbed] for use in sending messages.
 */
@DiskordDsl
fun message(block: CombinedMessageEmbed.() -> Unit) = CombinedMessageEmbed().apply(block)

/**
 * DSL command to add an image to an embed.
 *
 * @param url The url of the image.
 * @param block The optional DSL lambda in which to specify properties of the image.
 */
@DiskordDsl
fun CombinedMessageEmbed.image(url: String, block: EmbedImage.() -> Unit = {}) {
    this.image = EmbedImage(url).apply(block)
}

/**
 * DSL command to add a video to an embed.
 *
 * @param url The url of the video.
 * @param block The optional DSL lambda in which to specify properties of the image.
 */
@DiskordDsl
fun CombinedMessageEmbed.video(url: String, block: EmbedVideo.() -> Unit = {}) {
    video = EmbedVideo(url).apply(block)
}

/**
 * DSL command to add a provider field to an embed.
 *
 * @param name A provider of the embed.
 * @param url The url for the provider.
 */
@DiskordDsl
fun CombinedMessageEmbed.provider(name: String, url: String) {
    provider = EmbedProvider(name, url)
}

/**
 * DSL command to add an author field to an embed.
 *
 * @param name The author of the embed.
 * @param block The optional DSL lambda in which to specify properties of the author.
 */
@DiskordDsl
fun CombinedMessageEmbed.author(name: String, block: EmbedAuthor.() -> Unit = {}) {
    author = EmbedAuthor(name).apply(block)
}

/**
 * DSL command to add a footer to an embed.
 *
 * @param text The text of the footer.
 * @param block The optional DSL lambda in which to specify properties of the footer.
 */
@DiskordDsl
fun CombinedMessageEmbed.footer(text: String, block: EmbedFooter.() -> Unit = {}) {
    footer = EmbedFooter(text).apply(block)
}

/**
 * DSL command to add a field to a set of fields in an embed.
 *
 * @param name The name of the field.
 * @param value The value of the field.
 * @param inline Should this field be displayed inline.
 */
@DiskordDsl
fun CombinedMessageEmbed.field(name: String, value: String, inline: Boolean) = fields.add(EmbedField(name, value, inline))
