package com.example.chatlek.data.repository

import com.example.chatlek.data.datasource.DataSource
import javax.inject.Inject

class UserRepository @Inject constructor(var dataSource: DataSource) {

    suspend fun createUser(name: String, lastName: String, picURL: String) =
        dataSource.createUser(name, lastName, picURL)

    suspend fun getUser() = dataSource.getUser()

    suspend fun updateUser(name: String, lastName: String, picURL: String) =
        dataSource.updateUser(name, lastName, picURL)

    suspend fun getFriends() = dataSource.getFriends()

    suspend fun createFriendsCode(code: String) = dataSource.createFriendsCode(code = code)

    suspend fun useFriendsCode(code: String) = dataSource.useFriendsCode(code = code)
}