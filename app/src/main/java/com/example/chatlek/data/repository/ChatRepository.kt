package com.example.chatlek.data.repository

import com.example.chatlek.data.datasource.DataSource
import com.example.chatlek.data.entity.chat.ChatMessage
import javax.inject.Inject

class ChatRepository @Inject constructor(
    var dataSource: DataSource
) {
    suspend fun createChat(receiverId: String, lastMessage: String) =
        dataSource.createChat(receiverId, lastMessage)

    suspend fun getChat(receiverId: String) = dataSource.getChat(receiverId)

    suspend fun updateChat(receiverId: String, lastMessage: String) =
        dataSource.updateChat(receiverId, lastMessage)

    suspend fun connectWebSocket(onMessageReceived: (ChatMessage) -> Unit) =
        dataSource.connectWebSocket(onMessageReceived)

    suspend fun sendMessage(message: ChatMessage) = dataSource.sendMessage(message)

    suspend fun disconnectWebSocket() = dataSource.disconnectWebSocket()
}