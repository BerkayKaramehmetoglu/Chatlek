package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest(
    val id: String,
    val code: String
)
