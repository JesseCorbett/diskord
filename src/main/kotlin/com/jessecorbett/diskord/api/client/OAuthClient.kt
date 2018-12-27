package com.jessecorbett.diskord.api.client

import com.fasterxml.jackson.module.kotlin.readValue
import com.jessecorbett.diskord.api.rest.BearerToken
import com.jessecorbett.diskord.api.exception.DiscordUnauthorizedException
import com.jessecorbett.diskord.internal.httpClient
import com.jessecorbett.diskord.internal.jsonMapper
import okhttp3.*
import org.slf4j.LoggerFactory
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


private const val AUTH_CODE = "authorization_code"
private const val REFRESH_TOKEN = "refresh_token"

class OAuthClient(private val clientId: String, private val clientSecret: String, private val redirectUri: String) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private suspend fun getToken(code: String, grantType: String): BearerToken {
        val formBody = FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("grant_type", grantType)
                .add("redirect_uri", redirectUri)
        if (grantType == AUTH_CODE) {
            formBody.add("code", code)
        } else if (grantType == REFRESH_TOKEN) {
            formBody.add("refresh_token", code)
        }

        val request = Request.Builder().post(formBody.build()).url("https://discordapp.com/api/oauth2/token").build()

        return suspendCoroutine { cont ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, exception: IOException?) {
                    cont.resumeWithException(exception!!)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    try {
                        cont.resume(jsonMapper.readValue(response!!.body()!!.string()))
                    } catch (e: Exception) {
                        logger.error("Failed to authenticate against discord", e)
                        cont.resumeWithException(DiscordUnauthorizedException())
                    }
                }

            })
        }
    }

    suspend fun getAccessToken(token: String): BearerToken {
        return getToken(token, AUTH_CODE)
    }

    suspend fun getFromRefreshToken(token: String): BearerToken {
        return getToken(token, REFRESH_TOKEN)
    }
}
