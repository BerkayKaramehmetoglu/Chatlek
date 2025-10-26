package com.example.chatlek.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.ktor.ApiClient
import kotlinx.coroutines.launch

class ChatViewModel(private val apiClient: ApiClient): ViewModel() {

    fun startWebSocket() {
        viewModelScope.launch {
            try {
                apiClient.connectWebSocket()
            } catch (e: Exception) {
                println("❌ WebSocket hatası: ${e.message}")
            }
        }
    }
}