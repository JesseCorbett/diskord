package com.jessecorbett.diskord.api.model

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerialName("id") val id: String,
    @SerialName("filename") val fileName: String,
    @SerialName("size") val sizeInBytes: Int,
    @SerialName("url") val url: String,
    @SerialName("proxy_url") val proxiedUrl: String,
    @Optional @SerialName("height") val imageHeight: Int? = null,
    @Optional @SerialName("width") val imageWidth: Int? = null
)
