package com.example.chatlek.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient(OkHttp) {

        defaultRequest { url(urlString = "http://10.0.2.2:3000/") }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(json = Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000L
            connectTimeoutMillis = 10000L
            socketTimeoutMillis = 10000L
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend fun postFriends(code: String) {
        val response = client.post(urlString = "friends") {
            setBody(code)
        }
    }//uı tasarımı yapıldıktan sonra dönülecek
}