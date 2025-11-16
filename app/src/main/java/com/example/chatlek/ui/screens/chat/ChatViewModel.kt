package com.example.chatlek.ui.screens.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.data.entity.chat.ChatMessage
import com.example.chatlek.data.entity.chat.GetChat
import com.example.chatlek.data.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private var chatRepository: ChatRepository,
    private var auth: FirebaseAuth,
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    var chats = MutableLiveData<GetChat>()
        private set

    private fun startWebSocket() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                chatRepository.connectWebSocket { received ->
                    _messages.update { it + received }
                }
            } catch (e: Exception) {
                println("WebSocket Error: $e")
            }
        }
    }

    fun sendMessage(text: String) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                val myMessage = ChatMessage(
                    id = auth.currentUser!!.uid,
                    message = text,
                    isMine = true
                )
                _messages.update { it + myMessage }
                chatRepository.sendMessage(myMessage)
            }
        } catch (e: Exception) {
            println("Send Message Error $e")
        }
    }

    fun disconnectWebSocket() {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                chatRepository.disconnectWebSocket()
            }
        } catch (e: Exception) {
            println("Disconnection Error $e")
        }
    }

    private fun createChat(receiverId: String, lastMessage: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                chatRepository.createChat(receiverId = receiverId, lastMessage = lastMessage)
            } catch (e: Exception) {
                println("Create Chat Error: $e")
            }
        }
    }

    fun getChat(receiverId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                startWebSocket()
                val response = chatRepository.getChat(receiverId = receiverId)
                chats.value = response
            } catch (e: Exception) {
                println("Get Chat Error $e")
            }
        }
    }

    fun getChatForUser(receiverId: String): LiveData<GetChat> {
        val result = MutableLiveData<GetChat>()
        viewModelScope.launch(Dispatchers.Main) {
            result.postValue(chatRepository.getChat(receiverId))
        }
        return result
    }

    fun updateChat(receiverId: String, lastMessage: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                createChat(receiverId = receiverId, lastMessage = lastMessage)
                val response =
                    chatRepository.updateChat(receiverId = receiverId, lastMessage = lastMessage)
                chats.value = response
            } catch (e: Exception) {
                println("Update Chat Error: $e")
            }
        }
    }
}