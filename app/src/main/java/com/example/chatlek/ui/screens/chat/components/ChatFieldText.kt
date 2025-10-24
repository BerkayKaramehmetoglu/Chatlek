package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chatlek.R
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Green
import com.example.chatlek.ui.theme.White


@Composable
fun ChatFieldText(modifier: Modifier = Modifier) {
    var message by remember { mutableStateOf("") }

    TextField(
        value = message,
        onValueChange = { message = it },
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.write_message), color = Green) },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Black_Out,
            unfocusedContainerColor = Black_Out,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = White,
            unfocusedTextColor = White,
        ),
        modifier = modifier
    )
}