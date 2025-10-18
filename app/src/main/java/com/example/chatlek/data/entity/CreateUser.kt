package com.example.chatlek.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(
    val id: String,
    val name: String,
    val lastName: String,
    val picURL: String,
    val email: String
)
