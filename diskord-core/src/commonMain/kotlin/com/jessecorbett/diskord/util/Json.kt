package com.jessecorbett.diskord.util

import kotlinx.serialization.json.Json
import com.jessecorbett.diskord.api.common.audit.AuditLogChange
import com.jessecorbett.diskord.api.common.audit.AuditLogEntry


/**
 * Our base [Json] configuration
 */
internal val defaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * [Json] instance that is the same as everything else but used to deserialize [AuditLogEntry]
 */
internal val auditLogEntryJson = Json(defaultJson) {
    classDiscriminator = "action_type"
}

/**
 * [Json] instance that is the same as everything else but used to deserialize [AuditLogChange]
 */
internal val auditLogChangeJson = Json(defaultJson) {
    classDiscriminator = "key"
}

/**
 * Relaxed [Json] instance that omits null values from (de-)serialized objects
 */
internal val omitNullsJson = Json(defaultJson) {
    encodeDefaults = false
}
