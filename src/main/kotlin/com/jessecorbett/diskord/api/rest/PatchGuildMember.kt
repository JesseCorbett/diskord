package com.jessecorbett.diskord.api.rest

import com.fasterxml.jackson.annotation.JsonProperty

data class PatchGuildMember(
        @JsonProperty("nick") val nickname: String? = null,
        @JsonProperty("roles") val roleIds: List<String>? = null,
        @JsonProperty("mute") val mute: Boolean? = null,
        @JsonProperty("deaf") val deaf: Boolean? = null,
        @JsonProperty("channel_id") val channelId: String? = null
)
