package com.example.chatlek.ui.screens.profile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.theme.White

@Composable
fun FieldsText(
    value: TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
) {
    TextField(
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = label,
                fontWeight = FontWeight.W300,
                color = Black
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = White.copy(alpha = 0.5f),
            focusedContainerColor = White.copy(alpha = 0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledContainerColor = White.copy(alpha = 0.5f),
        )
    )
}