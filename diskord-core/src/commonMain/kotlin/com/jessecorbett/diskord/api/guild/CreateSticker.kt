package com.jessecorbett.diskord.api.guild

import com.jessecorbett.diskord.api.channel.FileData

public data class CreateSticker(
    val name: String,
    val description: String,
    val tags: String,
    val file: FileData,
)
