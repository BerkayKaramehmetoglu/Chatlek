package com.example.chatlek.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.ktor.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val apiClient: ApiClient) : ViewModel() {

    var message = MutableStateFlow("")
        private set

    fun generateRandomCode(length: Int = 4): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    fun createFriendsCode(generatedCode: String) {
        viewModelScope.launch {
            apiClient.createFriendsCode(code = generatedCode)
        }
    }

    fun useFriendsCode(friendsCode: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = apiClient.useFriendsCode(code = friendsCode)
            if (!response.success) {
                message.update {
                    response.message
                }
            } else {
                clearMessage()
                onSuccess()
            }
        }
    }

    private fun clearMessage() {
        message.value = ""
    }
}