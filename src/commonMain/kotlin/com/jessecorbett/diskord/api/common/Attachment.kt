package com.jessecorbett.diskord.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerialName("id") val id: String,
    @SerialName("filename") val fileName: String,
    @SerialName("size") val sizeInBytes: Int,
    @SerialName("url") val url: String,
    @SerialName("proxy_url") val proxiedUrl: String,
    @SerialName("height") val imageHeight: Int? = null,
    @SerialName("width") val imageWidth: Int? = null
)
