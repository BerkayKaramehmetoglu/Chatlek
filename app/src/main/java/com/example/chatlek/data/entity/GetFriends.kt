package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class GetFriends(
    val friends: List<String>
)