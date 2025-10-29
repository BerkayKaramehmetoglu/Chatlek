package com.example.chatlek.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    @SerialName("_id")
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val profilePic: String
)
