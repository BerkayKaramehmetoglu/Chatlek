package com.example.chatlek.data.entity.chat

import kotlinx.serialization.Serializable

@Serializable
data class CreateChat(
    val senderId: String,
    val receiverId: String,
    val lastMessage: String
)
