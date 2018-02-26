package com.jessecorbett.diskord

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

val jsonMapper = ObjectMapper().findAndRegisterModules().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

private const val token = "MzQ2NDQ0NjE1ODMxNzgxMzc2.DW8y7Q.jgJgT3EBRyAzi7CeoEt8-CXPBhA"

fun main(args: Array<String>) {
    val discordSocket = DiscordConnection(token, eventListener = Listener())

    while (true) {

    }
}

class Listener : EventListener()

// This class is basically just an in-progress testing resource, will eventually be removed as testing is no longer necessary
