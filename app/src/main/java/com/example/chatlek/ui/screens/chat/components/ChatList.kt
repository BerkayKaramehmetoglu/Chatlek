package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatlek.ui.screens.chat.examp.Message


@Composable
fun ChatList(modifier: Modifier = Modifier) {
    val messages = listOf(
        Message("Selam, nasılsın?", true),
        Message("İyiyim sen?", false),
        Message("Ben de iyiyim, ne yapıyorsun?", true),
        Message("Bir şeyler kodluyorum 😄", false)
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(messages) { message ->
            ChatBubble(message)
        }
    }
}