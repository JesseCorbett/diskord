package com.jessecorbett.diskord.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Attachment(
        @JsonProperty("id") val id: String,
        @JsonProperty("filename") val fileName: String,
        @JsonProperty("size") val sizeInBytes: Int,
        @JsonProperty("url") val url: String,
        @JsonProperty("proxy_url") val proxiedUrl: String,
        @JsonProperty("height") val imageHeight: Int?,
        @JsonProperty("width") val imageWidth: Int?
)
