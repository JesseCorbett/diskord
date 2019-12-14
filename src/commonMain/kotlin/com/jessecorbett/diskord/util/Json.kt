package com.jessecorbett.diskord.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


/**
 * [JsonConfiguration] that merges [Json.nonstrict] with [JsonConfiguration.Stable].
 */
internal val jsonConfiguration = JsonConfiguration.Stable.copy(strictMode = false, useArrayPolymorphism = true)

/**
 * Default [Json] instance.
 */
internal val defaultJson = Json(jsonConfiguration)

/**
 * Relaxed [Json] instance that omits null values from (de-)serialized objects.
 */
internal val relaxedJson = Json(jsonConfiguration.copy(encodeDefaults = false))
