package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatlek.ui.screens.chat.ChatViewModel
import com.example.chatlek.data.entity.chat.Message

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel
) {
    val messages by chatViewModel.messages.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
    ) {
        items(messages) { message ->
            ChatBubble(
                Message(
                    text = message.message,
                    isFromMe = message.isMine
                )
            )
        }
    }
}
