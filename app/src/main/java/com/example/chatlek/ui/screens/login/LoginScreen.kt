package com.example.chatlek.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatlek.R
import com.example.chatlek.firebase.AuthState
import com.example.chatlek.firebase.AuthViewModel
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.theme.DarkMatBlue
import com.example.chatlek.ui.theme.Gray
import com.example.chatlek.ui.theme.White

@Composable
fun LoginScreen(navHostController: NavHostController, authViewModel: AuthViewModel) {

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
            .background(DarkMatBlue)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
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
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 64.sp,
                fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                color = Black
            )
        }

        Column(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth()
        ) {
            FilledCard(
                onClick = { navHostController.navigate(Screen.Register.route) },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onClickLogin = { authViewModel.login(email.text, password.text) }
            )
        }
    }
}

@Composable
private fun FilledCard(
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    onClick: () -> Unit,
    onClickLogin: () -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topEnd = 120.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(R.string.login).uppercase(),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                color = Black
            )

            Text(
                text = stringResource(R.string.sign_in_to_continue),
                fontSize = 16.sp,
                color = Gray
            )

            TextField(
                modifier = Modifier.padding(top = 32.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                value = email,
                onValueChange = onEmailChange,
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        fontWeight = FontWeight.W300,
                        color = Black
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray.copy(alpha = 0.5f),
                    focusedContainerColor = Gray.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            TextField(
                modifier = Modifier.padding(top = 24.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                value = password,
                onValueChange = onPasswordChange,
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        fontWeight = FontWeight.W300,
                        color = Black
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray.copy(alpha = 0.5f),
                    focusedContainerColor = Gray.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            FilledButton(onClickLogin = onClickLogin)

            ButtonText(onClick = onClick)
        }
    }
}

@Composable
private fun FilledButton(onClickLogin: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(top = 24.dp)
            .size(width = 240.dp, height = 56.dp),
        onClick = { onClickLogin() },
        colors = ButtonDefaults.buttonColors(Black),
        shape = RoundedCornerShape(16.dp)
    )
    {
        Text(
            text = stringResource(R.string.login),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ButtonText(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(R.string.signup),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}