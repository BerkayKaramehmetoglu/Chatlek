package com.example.chatlek.ui.screens.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.data.entity.GetChat
import com.example.chatlek.ktor.ApiClient
import kotlinx.coroutines.launch

class ChatViewModel(private val apiClient: ApiClient) : ViewModel() {
    var chats = MutableLiveData<GetChat>()
        private set

    fun startWebSocket() {
        viewModelScope.launch {
            try {
                apiClient.connectWebSocket()
            } catch (e: Exception) {
                println("❌ WebSocket hatası: ${e.message}")
            }
        }
    }

    fun createChat(receiverId: String, lastMessage: String) {
        viewModelScope.launch {
            try {
                apiClient.createChat(receiverId = receiverId, lastMessage = lastMessage)
            } catch (e: Exception) {
                println("Create Chat Error: $e")
            }
        }
    }

    fun getChat(receiverId: String) {
        viewModelScope.launch {
            try {
                val response = apiClient.getChat(receiverId = receiverId)
                chats.value = response
            } catch (e: Exception) {
                println("Get Chat Error $e")
            }
        }
    }
}