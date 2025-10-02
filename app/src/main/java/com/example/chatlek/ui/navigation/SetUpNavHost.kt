package com.example.chatlek.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatlek.ui.screens.login.LoginScreen
import com.example.chatlek.ui.screens.register.RegisterScreen

@Composable
fun SetUpNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Register.route) {

        composable(Screen.Login.route) {
            LoginScreen()
        }

        composable(Screen.Register.route) {
            RegisterScreen()
        }
    }
}