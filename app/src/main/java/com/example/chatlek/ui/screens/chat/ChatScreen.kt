package com.example.chatlek.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatlek.data.entity.GetUser
import com.example.chatlek.ui.screens.chat.components.ChatFieldText
import com.example.chatlek.ui.screens.chat.components.ChatList
import com.example.chatlek.ui.screens.chat.components.TopBar
import com.example.chatlek.ui.theme.Black

@Composable
fun ChatScreen(
    navHostController: NavHostController,
    chatUser: GetUser,
    chatViewModel: ChatViewModel
) {

    Scaffold(
        topBar = {
            TopBar(chatUser = chatUser, navHostController)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Black)
                .imePadding()
                .navigationBarsPadding()
        ) {
            ChatList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                chatViewModel = chatViewModel
            )

            ChatFieldText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                chatViewModel = chatViewModel,
                chatUser = chatUser
            )
        }
    }
}
