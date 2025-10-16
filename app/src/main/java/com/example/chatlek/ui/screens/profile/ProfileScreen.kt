package com.example.chatlek.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatlek.R
import com.example.chatlek.ui.screens.profile.components.CardFilled
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.theme.White

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
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
                .weight(2.5f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CardFilled(
                name = name,
                onNameChange = { name = it },
                lastName = lastname,
                onLastNameChange = { lastname = it },
                email = email,
                onEmailChange = { email = it })
        }
    }
}