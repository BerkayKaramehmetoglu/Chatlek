package com.example.chatlek.di

import com.example.chatlek.data.datasource.DataSource
import com.example.chatlek.data.repository.ChatRepository
import com.example.chatlek.data.repository.UserRepository
import com.example.chatlek.ktor.ApiClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppClient(): ApiClient {
        return ApiClient()
    }

    @Provides
    @Singleton
    fun provideDataSource(apiClient: ApiClient): DataSource {
        return DataSource(apiClient = apiClient)
    }

    @Provides
    @Singleton
    fun provideChatRepository(dataSource: DataSource): ChatRepository {
        return ChatRepository(dataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: DataSource): UserRepository {
        return UserRepository(dataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): Firebase {
        return Firebase
    }
}