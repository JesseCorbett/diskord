package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty
import com.jessecorbett.diskord.api.models.ChannelType
import com.jessecorbett.diskord.api.models.Overwrite

data class CreateChannel(
        @JsonProperty("name") val name: String,
        @JsonProperty("type") val type: ChannelType,
        @JsonProperty("bitrate") val bitrate: Int? = null,
        @JsonProperty("user_limit") val voiceChannelUserLimit: Int? = null,
        @JsonProperty("permissions_overwrites") val overwrites: List<Overwrite> = emptyList(),
        @JsonProperty("parent_id") val categoryId: String? = null,
        @JsonProperty("nsfw") val nsfw: Boolean
)
