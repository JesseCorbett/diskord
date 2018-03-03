package com.jessecorbett.diskord

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient

internal val httpClient = OkHttpClient.Builder().cache(null).build()

internal val jsonMapper = ObjectMapper().findAndRegisterModules().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)!!
