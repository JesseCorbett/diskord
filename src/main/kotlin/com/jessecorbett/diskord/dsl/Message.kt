package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.rest.*

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
 * DSL command to add a set of fields to an embed.
 *
 * @param block The DSL lambda in which to specify the fields.
 */
@DiskordDsl
fun Embed.fields(block: MutableList<EmbedField>.() -> Unit) = fields.apply(block)

/**
 * DSL command to add a field to a set of fields in an embed.
 *
 * @param name The name of the field.
 * @param value The value of the field.
 * @param inline Should this field be displayed inline.
 */
@DiskordDsl
fun MutableList<EmbedField>.field(name: String, value: String, inline: Boolean) = add(EmbedField(name, value, inline))
