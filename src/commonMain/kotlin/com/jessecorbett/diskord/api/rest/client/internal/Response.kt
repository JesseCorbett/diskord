package com.jessecorbett.diskord.api.rest.client.internal

import com.jessecorbett.diskord.util.DiskordInternals

@DiskordInternals
data class Response(val code: Int, val body: String?, val headers: Map<String, String?>)
