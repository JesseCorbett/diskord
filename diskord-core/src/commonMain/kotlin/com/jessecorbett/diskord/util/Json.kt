package com.jessecorbett.diskord.util

import kotlinx.serialization.json.Json


/**
 * Our base [Json] configuration
 */
internal val defaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    coerceInputValues = true
}

/**
 * Relaxed [Json] instance that omits null values from (de-)serialized objects
 */
internal val omitNullsJson = Json(defaultJson) {
    encodeDefaults = false
}
