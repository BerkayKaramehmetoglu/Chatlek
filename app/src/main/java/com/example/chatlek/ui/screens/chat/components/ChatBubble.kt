package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chatlek.data.entity.chat.Message
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Green
import com.example.chatlek.ui.theme.White

@Composable
fun ChatBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromMe)
                    Green
                else
                    Black_Out
            ),
            modifier = Modifier
                .widthIn(max = 280.dp)
                .padding(4.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier
                    .padding(12.dp),
                textAlign = TextAlign.Start,
                color = White
            )
        }
    }
}