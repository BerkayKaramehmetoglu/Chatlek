package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    val name: String,
    val lastName: String,
    val email: String,
    val profilePic: String
)
