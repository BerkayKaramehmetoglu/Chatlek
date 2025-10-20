package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUser(
    val id: String,
    val name: String,
    val lastName: String,
    val picURL: String
)
