package com.example.chatlek.data.entity.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val id: String,
    val message: String,
    val isMine: Boolean = false
)
