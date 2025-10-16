package com.example.chatlek.ui.screens.rlcomponents

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.chatlek.ui.theme.Gray

@Composable
fun ButtonText(onClick: () -> Unit, string: String) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(
            text = string,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Gray
        )
    }
}