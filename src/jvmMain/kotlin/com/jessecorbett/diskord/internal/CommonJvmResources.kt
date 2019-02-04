package com.jessecorbett.diskord.internal

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal val httpClient = OkHttpClient.Builder()
        .cache(null)
        .connectionPool(ConnectionPool(1, 5, TimeUnit.SECONDS))
        .build()
