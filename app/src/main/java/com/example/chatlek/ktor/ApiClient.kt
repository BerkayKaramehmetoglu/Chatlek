package com.example.chatlek.ktor

import com.example.chatlek.data.entity.ApiResponse
import com.example.chatlek.data.entity.ChatMessage
import com.example.chatlek.data.entity.CreateUser
import com.example.chatlek.data.entity.GetUser
import com.example.chatlek.data.entity.MessageRequest
import com.example.chatlek.data.entity.UpdateUser
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class ApiClient {
    private val timeOut = 10000L
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
            requestTimeoutMillis = timeOut
            connectTimeoutMillis = timeOut
            socketTimeoutMillis = timeOut
        }

        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser

    suspend fun createFriendsCode(code: String): ApiResponse {
        return client.post(urlString = "friends/create") {
            if (code.isNotEmpty()) {
                setBody(MessageRequest(code = code, id = user?.uid!!))
            }
        }.body()
    }

    suspend fun useFriendsCode(code: String): ApiResponse {
        return client.post(urlString = "friends/use") {
            setBody(MessageRequest(code = code, id = user?.uid!!))
        }.body()
    }

    suspend fun createUser(name: String, lastName: String, picURL: String): ApiResponse {
        return client.post(urlString = "create_user") {
            setBody(
                CreateUser(
                    id = user?.uid!!,
                    name = name,
                    lastName = lastName,
                    picURL = picURL,
                    email = user?.email!!
                )
            )
        }.body()
    }

    suspend fun getFriends(): List<GetUser> {
        val id = auth.currentUser!!.uid
        return client.get(urlString = "get_friends/$id").body<List<GetUser>>()
    }

    suspend fun updateUser(name: String, lastName: String, picURL: String): ApiResponse {
        return client.post(urlString = "update_user") {
            setBody(
                UpdateUser(
                    id = user?.uid!!,
                    name = name,
                    lastName = lastName,
                    picURL = picURL
                )
            )
        }.body()
    }

    suspend fun getUser(): GetUser {
        val id = auth.currentUser!!.uid
        val response: GetUser = client.get(urlString = "get_user/$id").body()
        return response
    }

    suspend fun connectWebSocket() {
        client.webSocket(
            method = HttpMethod.Get,
            host = "10.0.2.2",
            port = 8080,
        ) {
            println("WebSocket bağlantısı kuruldu")

            sendSerialized(ChatMessage(id = auth.currentUser!!.uid, message = "Merhaba"))

            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    try {
                        val received = Json.decodeFromString<ChatMessage>(text)
                        println("Mesaj: ${received.id}: ${received.message}")
                    } catch (e: Exception) {
                        println("Mesaj parse hatası: ${e.message}")
                    }
                }
            }
        }
    }
}