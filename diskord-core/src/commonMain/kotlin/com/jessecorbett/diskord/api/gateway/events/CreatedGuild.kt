package com.jessecorbett.diskord.api.gateway.events

import com.jessecorbett.diskord.api.common.*
import com.jessecorbett.diskord.util.defaultJson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = CreatedGuildSerializer::class)
public sealed interface CreatedGuild {
    public val id: String
    public val unavailable: Boolean
}

@Serializable
public data class AvailableGuild(
    val guild: Guild,
    val extras: AvailableGuildExtras
) : CreatedGuild {
    override val id: String by guild::id
    override val unavailable: Boolean by extras::unavailable
}

@Serializable
public data class AvailableGuildExtras(
    @SerialName("joined_at") val joinedAt: String,
    val large: Boolean,
    val unavailable: Boolean = false,
    @SerialName("member_count") val memberCount: Int,
    @SerialName("voice_states") val voiceStates: List<VoiceState>,
    val members: List<GuildMember>,
    val channels: List<Channel>,
    val threads: List<GuildThread>,
    val presences: List<PresenceUpdate>,
    @SerialName("stage_instances") val stageInstances: List<StageInstance>,
)

@Serializable
public data class UnavailableGuild(
    override val id: String,
    override val unavailable: Boolean
) : CreatedGuild

/**
 * A real black magic class to avoid re-defining fields where possible
 */
internal object CreatedGuildSerializer : KSerializer<CreatedGuild> {
    override val descriptor: SerialDescriptor = JsonElement.serializer().descriptor

    override fun deserialize(decoder: Decoder): CreatedGuild {
        val json = decoder.decodeSerializableValue(JsonElement.serializer()) as JsonObject
        val unavailable = json.getValue("unavailable").jsonPrimitive.boolean
        return if (unavailable) {
            defaultJson.decodeFromJsonElement(UnavailableGuild.serializer(), json)
        } else {
            AvailableGuild(
                guild = defaultJson.decodeFromJsonElement(Guild.serializer(), json),
                extras = defaultJson.decodeFromJsonElement(AvailableGuildExtras.serializer(), json)
            )
        }
    }

    override fun serialize(encoder: Encoder, value: CreatedGuild) {
        val json = when (value) {
            is AvailableGuild -> defaultJson.encodeToJsonElement(value)
            is UnavailableGuild -> defaultJson.encodeToJsonElement(value)
        }
        encoder.encodeSerializableValue(JsonElement.serializer(), json)
    }
}
