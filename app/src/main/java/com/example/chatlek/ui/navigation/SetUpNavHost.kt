package com.example.chatlek.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatlek.ui.screens.LoginScreen

@Composable
fun SetUpNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}