package com.example.chatlek.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.screens.chat.ChatViewModel
import com.example.chatlek.ui.screens.home.HomeViewModel
import com.example.chatlek.ui.theme.Black
import com.example.chatlek.ui.theme.Black_Out

@Composable
fun ChatList(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    chatViewModel: ChatViewModel
) {
    val friends: State<List<GetUser>?> = homeViewModel.friends.observeAsState()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Black_Out,
            contentColor = Black_Out
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            friends.value?.let {
                items(it) { friends ->
                    chatViewModel.getChat(friends.id)
                    ChatCard(
                        friends, onClick = {
                            navHostController.navigate(
                                Screen.Chat(
                                    chatUser = friends
                                )
                            )
                        },
                        chatViewModel = chatViewModel
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    HorizontalDivider(color = Black)
                }
            }
        }
    }
}