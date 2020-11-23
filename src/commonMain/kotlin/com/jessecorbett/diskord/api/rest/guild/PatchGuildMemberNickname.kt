package com.jessecorbett.diskord.api.rest.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchGuildMemberNickname(@SerialName("nick") val nickname: String)
