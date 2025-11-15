package com.example.chatlek.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.firebase.auth.AuthViewModel
import com.example.chatlek.ui.screens.chat.ChatScreen
import com.example.chatlek.ui.screens.chat.ChatViewModel
import com.example.chatlek.ui.screens.home.HomeScreen
import com.example.chatlek.ui.screens.home.HomeViewModel
import com.example.chatlek.ui.screens.login.LoginScreen
import com.example.chatlek.ui.screens.profile.ProfileScreen
import com.example.chatlek.ui.screens.profile.ProfileViewModel
import com.example.chatlek.ui.screens.register.RegisterScreen
import kotlin.reflect.typeOf

@Composable
fun SetUpNavHost(
    navHostController: NavHostController
) {
    NavHost(navHostController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(navHostController = navHostController, authViewModel = authViewModel)
        }

        composable(Screen.Register.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(navHostController = navHostController, authViewModel = authViewModel)
        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val authViewModel: AuthViewModel = hiltViewModel()
            val chatViewModel: ChatViewModel = hiltViewModel()
            HomeScreen(
                navHostController = navHostController,
                authViewModel = authViewModel,
                homeViewModel = homeViewModel,
                chatViewModel = chatViewModel
            )
        }

        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(profileViewModel = profileViewModel)
        }

        composable<Screen.Chat>(
            typeMap = mapOf(
                typeOf<GetUser>() to CustomNavType.ChatType
            )
        ) {
            val chatViewModel: ChatViewModel = hiltViewModel()
            val arguments = it.toRoute<Screen.Chat>()
            ChatScreen(
                navHostController = navHostController,
                chatUser = arguments.chatUser,
                chatViewModel = chatViewModel
            )
        }
    }
}