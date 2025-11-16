package com.example.chatlek.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatlek.R
import com.example.chatlek.firebase.auth.AuthState
import com.example.chatlek.firebase.auth.AuthViewModel
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.screens.rlcomponents.ButtonText
import com.example.chatlek.ui.screens.rlcomponents.FilledCard
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.White

@Composable
fun RegisterScreen(navHostController: NavHostController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navHostController.navigate(Screen.Home.route)
            is AuthState.Error -> Toast.makeText(
                context, (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black_Out)
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(R.mipmap.ic_launcher_monochrome),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = White
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilledCard(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth(),
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onClicks = { authViewModel.signup(email.text, password.text) },
                string = stringResource(R.string.create_new)
            )
            ButtonText(
                onClick = { navHostController.popBackStack() },
                stringResource(R.string.already_registered_log_in_here_)
            )
        }
    }
}
