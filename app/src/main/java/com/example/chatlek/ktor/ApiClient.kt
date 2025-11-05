package com.example.chatlek.ktor

import com.example.chatlek.data.entity.client.ApiResponse
import com.example.chatlek.data.entity.chat.ChatMessage
import com.example.chatlek.data.entity.chat.CreateChat
import com.example.chatlek.data.entity.user.CreateUser
import com.example.chatlek.data.entity.chat.GetChat
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.data.entity.client.MessageRequest
import com.example.chatlek.data.entity.user.UpdateUser
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
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
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

    suspend fun createChat(receiverId: String, lastMessage: String): ApiResponse {
        val id = auth.currentUser!!.uid
        return client.post(urlString = "create_chat") {
            setBody(
                CreateChat(
                    senderId = id,
                    receiverId = receiverId,
                    lastMessage = lastMessage
                )
            )
        }.body()
    }

    suspend fun getChat(receiverId: String): GetChat {
        val id = auth.currentUser!!.uid
        return client.get(urlString = "get_chat") {
            url {
                parameters.append("senderId", id)
                parameters.append("receiverId", receiverId)
            }
        }.body()
    }

    suspend fun updateChat(receiverId: String, lastMessage: String): GetChat {
        val id = auth.currentUser!!.uid
        return client.put("update_chat") {
            setBody(
                mapOf(
                    "senderId" to id,
                    "receiverId" to receiverId,
                    "lastMessage" to lastMessage
                )
            )
        }.body()
    }

    private var socketSession: DefaultClientWebSocketSession? = null
    suspend fun connectWebSocket(onMessageReceived: (ChatMessage) -> Unit) {
        try {
            client.webSocket(urlString = "ws://10.0.2.2:8080") {
                socketSession = this
                println("WebSocket bağlantısı kuruldu")

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        try {
                            val received = Json.decodeFromString<ChatMessage>(text)
                            onMessageReceived(received)
                        } catch (e: Exception) {
                            println("Mesaj parse hatası: ${e.message}")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            println("WebSocket bağlantı hatası: ${e.message}")
        }
    }

    suspend fun sendMessage(message: ChatMessage) {
        try {
            val json = Json.encodeToString(ChatMessage.serializer(), message)
            socketSession?.send(Frame.Text(json))
            println("Mesaj gönderildi: ${message.message}")
        } catch (e: Exception) {
            println("Mesaj gönderme hatası: ${e.message}")
        }
    }
}