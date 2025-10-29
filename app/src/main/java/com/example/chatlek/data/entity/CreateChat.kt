package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreateChat(
    val senderId: String,
    val receiverId: String,
    val lastMessage: String
)
