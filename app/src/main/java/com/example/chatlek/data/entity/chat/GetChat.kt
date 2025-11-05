package com.example.chatlek.data.entity.chat

import kotlinx.serialization.Serializable

@Serializable
data class GetChat(
    val members: List<String>,
    val lastMessage: LastMessage
) {
    @Serializable
    data class LastMessage(
        val lastMessage: String,
        val senderId: String
    )
}
