package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatlek.R
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.ui.screens.chat.ChatViewModel
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Green
import com.example.chatlek.ui.theme.White

@Composable
fun ChatFieldText(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    chatUser: GetUser,
) {
    val chat = chatViewModel.chats.observeAsState()
    var message by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(chat) {
        chatViewModel.updateChat(chatUser.id, message.text)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
            modifier = Modifier
                .weight(1f)
        )
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .background(Green, shape = CircleShape),
            onClick = {
                if (message.text.isNotBlank()) {
                    chatViewModel.createChat(
                        receiverId = chatUser.id,
                        lastMessage = message.text.trim()
                    )
                    chatViewModel.sendMessage(text = message.text.trim())
                    message = TextFieldValue("")
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.outline_send_24),
                contentDescription = null,
                tint = White,
            )
        }
    }
}