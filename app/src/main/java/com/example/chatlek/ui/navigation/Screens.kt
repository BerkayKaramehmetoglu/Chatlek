package com.example.chatlek.ui.navigation

import com.example.chatlek.data.entity.GetUser
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    @Serializable
    data class Chat(val chatUser: GetUser) : Screen("chat")
}