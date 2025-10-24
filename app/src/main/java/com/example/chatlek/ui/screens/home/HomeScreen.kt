package com.example.chatlek.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatlek.R
import com.example.chatlek.firebase.auth.AuthState
import com.example.chatlek.firebase.auth.AuthViewModel
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.screens.home.components.ActionFloating
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.screens.home.components.TopBarMedium
import com.example.chatlek.ui.screens.home.components.ChatList
import com.example.chatlek.ui.screens.home.components.DialogAlert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()
    val message by homeViewModel.message.collectAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navHostController.navigate(Screen.Login.route)
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarMedium(
                authViewModel = authViewModel,
                scrollBehavior = scrollBehavior,
                navHostController = navHostController
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Black)
        ) {

            ChatList(homeViewModel = homeViewModel, navHostController = navHostController)

            ActionFloating(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { showDialog = true }
            )

            if (showDialog) {
                DialogAlert(
                    homeViewModel = homeViewModel,
                    dialogTitle = stringResource(R.string.add_friends),
                    onDismissRequest = {
                        showDialog = false
                        code = TextFieldValue(text = "")
                    },
                    onConfirmation = {
                        homeViewModel.useFriendsCode(
                            friendsCode = code.text,
                            onSuccess = { showDialog = false })
                        code = TextFieldValue(text = "")
                    },
                    code = code,
                    onCodeChange = { code = it },
                    message = message
                )
            }
        }
    }
}