package com.example.chatlek.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.data.entity.GetUser
import com.example.chatlek.ktor.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val apiClient: ApiClient) : ViewModel() {

    private val _user = MutableLiveData<GetUser>()
    val user: LiveData<GetUser> = _user

    var message = MutableStateFlow("")
        private set

    init {
        getUser()
    }

    fun createUser(
        name: String,
        lastName: String,
        picURL: String,
    ) {
        viewModelScope.launch {
            try {
                val response =
                    apiClient.createUser(name = name, lastName = lastName, picURL = picURL)
                if (!response.success) {
                    message.update {
                        response.message
                    }
                } else {
                    message.update {
                        response.message
                    }
                    delay(3000L)
                    clearMessage()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                val response = apiClient.getUser()
                _user.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUser(
        name: String,
        lastName: String,
        picURL: String,
    ) {
        viewModelScope.launch {
            try {
                val response =
                    apiClient.updateUser(name = name, lastName = lastName, picURL = picURL)
                if (response.success) {
                    getUser()
                    message.update {
                        response.message
                    }
                    delay(3000L)
                    clearMessage()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun clearMessage() {
        message.value = ""
    }
}