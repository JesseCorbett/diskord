package com.jessecorbett.diskord.dsl

import com.jessecorbett.diskord.api.rest.*

fun embed(block: Embed.() -> Unit) = Embed().apply(block)

fun image(url: String, block: EmbedImage.() -> Unit) = EmbedImage(url).apply(block)

fun Embed.addVideo(url: String, block: EmbedVideo.() -> Unit) {
    video = EmbedVideo(url).apply(block)
}

fun Embed.provider(name: String, url: String) {
    provider = EmbedProvider(name, url)
}

fun Embed.author(name: String, block: EmbedAuthor.() -> Unit) {
    author = EmbedAuthor(name).apply(block)
}

fun Embed.footer(text: String, block: EmbedFooter.() -> Unit) {
    footer = EmbedFooter(text).apply(block)
}

fun Embed.fields(block: MutableList<EmbedField>.() -> Unit) = fields.apply(block)

fun MutableList<EmbedField>.field(name: String, value: String, inline: Boolean) = add(EmbedField(name, value, inline))
