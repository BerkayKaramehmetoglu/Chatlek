package com.example.chatlek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatlek.ui.navigation.SetUpNavHost
import com.example.chatlek.ui.theme.ChatlekTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatlekTheme {
                Scaffold(    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0) ) { innerPadding ->
                    navController = rememberNavController()

                    Column(modifier = Modifier.padding(innerPadding)) {
                        SetUpNavHost(navHostController = navController)
                    }
                }
            }
        }
    }
}