package com.example.chatlek.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatlek.firebase.AuthViewModel
import com.example.chatlek.ui.screens.home.HomeScreen
import com.example.chatlek.ui.screens.home.HomeViewModel
import com.example.chatlek.ui.screens.login.LoginScreen
import com.example.chatlek.ui.screens.register.RegisterScreen

@Composable
fun SetUpNavHost(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(navHostController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(navHostController = navHostController, authViewModel = authViewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navHostController = navHostController, authViewModel = authViewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navHostController = navHostController,
                authViewModel = authViewModel,
                homeViewModel = homeViewModel
            )
        }
    }
}