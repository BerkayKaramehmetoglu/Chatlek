package com.example.chatlek.ui.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var userRepository: UserRepository) : ViewModel() {

    var message = MutableStateFlow("")
        private set
    var friends = MutableLiveData<List<GetUser>>(emptyList())
        private set

    init {
        getFriends()
    }

    fun generateRandomCode(length: Int = 4): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    fun createFriendsCode(generatedCode: String) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                userRepository.createFriendsCode(code = generatedCode)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    fun useFriendsCode(friendsCode: String, onSuccess: () -> Unit) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                val response = userRepository.useFriendsCode(code = friendsCode)
                if (!response.success) {
                    message.update {
                        response.message
                    }
                } else {
                    clearMessage()
                    onSuccess()
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun getFriends() {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                val response = userRepository.getFriends()
                friends.value = response
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    fun clearMessage() {
        message.value = ""
    }
}