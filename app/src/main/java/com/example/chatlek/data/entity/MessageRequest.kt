package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest(
    val userId: String,
    val email: String,
    val code: String
)
