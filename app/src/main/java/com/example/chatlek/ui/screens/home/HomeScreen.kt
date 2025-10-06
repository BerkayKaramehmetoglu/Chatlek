package com.example.chatlek.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatlek.R
import com.example.chatlek.firebase.AuthState
import com.example.chatlek.firebase.AuthViewModel
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.theme.Green
import com.example.chatlek.ui.theme.White

@Composable
fun HomeScreen(navHostController: NavHostController, authViewModel: AuthViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navHostController.navigate(Screen.Login.route)
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Home", fontSize = 26.sp)
            TextButton(onClick = { authViewModel.signOut() }) {
                Text("Sign out")
            }
        }

        ButtonAction(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { showDialog = true }
        )

        if (showDialog) {
            DialogAlert(
                dialogTitle = stringResource(R.string.add_friends),
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    // Confirm butonuna basılınca yapılacak işlemler
                    showDialog = false
                }
            )
        }
    }

}

@Composable
private fun ButtonAction(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = Green,
        contentColor = White
    ) {
        Icon(painterResource(R.drawable.baseline_add_24), "Floating action button.")
    }
}

@Composable
fun DialogAlert(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
) {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                fontSize = 32.sp,
                color = White
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Your Code: 123123123",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = White
                )

                TextField(
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    value = code,
                    onValueChange = { code = it },
                    label = {
                        Text(
                            text = stringResource(R.string.code),
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White.copy(alpha = 0.5f),
                        focusedContainerColor = White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = stringResource(R.string.add),
                    fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                    fontSize = 22.sp,
                    color = Black
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                    fontSize = 22.sp,
                    color = Black
                )
            }
        },
        containerColor = Green
    )
}