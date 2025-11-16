package com.example.chatlek.data.datasource

import com.example.chatlek.data.entity.chat.ChatMessage
import com.example.chatlek.ktor.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataSource @Inject constructor(var apiClient: ApiClient) {

    suspend fun createChat(receiverId: String, lastMessage: String) = withContext(Dispatchers.IO) {
        return@withContext apiClient.createChat(receiverId, lastMessage)
    }

    suspend fun getChat(receiverId: String) = withContext(Dispatchers.IO) {
        return@withContext apiClient.getChat(receiverId)
    }

    suspend fun updateChat(receiverId: String, lastMessage: String) = withContext(Dispatchers.IO) {
        return@withContext apiClient.updateChat(receiverId, lastMessage)
    }

    suspend fun connectWebSocket(onMessageReceived: (ChatMessage) -> Unit) =
        withContext(Dispatchers.IO) {
            return@withContext apiClient.connectWebSocket(onMessageReceived)
        }

    suspend fun sendMessage(message: ChatMessage) = withContext(Dispatchers.IO) {
        return@withContext apiClient.sendMessage(message)
    }

    suspend fun disconnectWebSocket() = withContext(Dispatchers.IO) {
        return@withContext apiClient.disconnectWebSocket()
    }

    suspend fun createUser(name: String, lastName: String, picURL: String) =
        withContext(Dispatchers.IO) {
            return@withContext apiClient.createUser(name, lastName, picURL)
        }

    suspend fun getUser() = withContext(Dispatchers.IO) {
        return@withContext apiClient.getUser()
    }

    suspend fun updateUser(name: String, lastName: String, picURL: String) =
        withContext(Dispatchers.IO) {
            return@withContext apiClient.updateUser(name, lastName, picURL)
        }

    suspend fun getFriends() =
        withContext(Dispatchers.IO) { return@withContext apiClient.getFriends() }

    suspend fun createFriendsCode(code: String) = withContext(Dispatchers.IO) {
        return@withContext apiClient.createFriendsCode(code = code)
    }

    suspend fun useFriendsCode(code: String) = withContext(Dispatchers.IO) {
        return@withContext apiClient.useFriendsCode(code = code)
    }
}