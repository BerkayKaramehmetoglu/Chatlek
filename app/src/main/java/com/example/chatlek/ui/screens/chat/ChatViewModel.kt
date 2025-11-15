package com.example.chatlek.ui.screens.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.data.entity.chat.ChatMessage
import com.example.chatlek.data.entity.chat.GetChat
import com.example.chatlek.ktor.ApiClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    var apiClient: ApiClient,
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    var chats = MutableLiveData<GetChat>()
        private set

    private fun startWebSocket() {
        viewModelScope.launch {
            try {
                apiClient.connectWebSocket { received ->
                    _messages.update { it + received }
                }
            } catch (e: Exception) {
                println("WebSocket Error: $e")
            }
        }
    }

    fun sendMessage(text: String) {
        try {
            viewModelScope.launch {
                val myMessage = ChatMessage(
                    id = auth.currentUser!!.uid,
                    message = text,
                    isMine = true
                )
                _messages.update { it + myMessage }
                apiClient.sendMessage(myMessage)
            }
        } catch (e: Exception) {
            println("Send Message Error $e")
        }
    }

    fun disconnectWebSocket() {
        try {
            viewModelScope.launch {
                apiClient.disconnectWebSocket()
            }
        } catch (e: Exception) {
            println("Disconnection Error $e")
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
                startWebSocket()
                val response = apiClient.getChat(receiverId = receiverId)
                chats.value = response
            } catch (e: Exception) {
                println("Get Chat Error $e")
            }
        }
    }

    fun updateChat(receiverId: String, lastMessage: String) {
        viewModelScope.launch {
            try {
                val response =
                    apiClient.updateChat(receiverId = receiverId, lastMessage = lastMessage)
                chats.value = response
            } catch (e: Exception) {
                println("Update Chat Error: $e")
            }
        }
    }
}