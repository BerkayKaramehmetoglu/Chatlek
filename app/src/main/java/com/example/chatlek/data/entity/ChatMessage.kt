package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val id: String,
    val message: String
)
