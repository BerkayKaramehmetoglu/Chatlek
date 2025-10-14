package com.example.chatlek.ui.screens.rlcomponents.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatlek.ui.theme.Black

@Composable
fun FilledButton(onClick: () -> Unit, string: String) {
    Button(
        modifier = Modifier
            .padding(top = 24.dp)
            .size(width = 240.dp, height = 56.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Black),
        shape = RoundedCornerShape(16.dp)
    )
    {
        Text(
            text = string,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}