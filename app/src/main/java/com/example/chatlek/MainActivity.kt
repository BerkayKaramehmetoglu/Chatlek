package com.example.chatlek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatlek.firebase.auth.AuthViewModel
import com.example.chatlek.ktor.ApiClient
import com.example.chatlek.ui.navigation.SetUpNavHost
import com.example.chatlek.ui.screens.home.HomeViewModel
import com.example.chatlek.ui.screens.profile.ProfileViewModel
import com.example.chatlek.ui.theme.ChatlekTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val authViewModel: AuthViewModel by viewModels()

    private val profileViewModel by viewModels<ProfileViewModel> {
        viewModelFactory {
            addInitializer(ProfileViewModel::class) {
                ProfileViewModel(apiClient = ApiClient())
            }
        }
    }
    private val homeViewModel by viewModels<HomeViewModel> {
        viewModelFactory {
            addInitializer(HomeViewModel::class) {
                HomeViewModel(apiClient = ApiClient())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatlekTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navController = rememberNavController()

                    Column(modifier = Modifier.padding(innerPadding)) {
                        SetUpNavHost(
                            navHostController = navController,
                            authViewModel = authViewModel,
                            homeViewModel = homeViewModel,
                            profileViewModel = profileViewModel
                        )
                    }
                }
            }
        }
    }
}