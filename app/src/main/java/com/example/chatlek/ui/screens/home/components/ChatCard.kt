package com.example.chatlek.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.ui.screens.chat.ChatViewModel
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Gray
import com.example.chatlek.ui.theme.White

@Composable
fun ChatCard(friends: GetUser, onClick: () -> Unit, chatViewModel: ChatViewModel) {
    val chat = chatViewModel.chats.observeAsState()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Black_Out,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = friends.profilePic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Black_Out)
                    .size(72.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${friends.name.capitalize()} ${friends.lastName.capitalize()}",
                    color = White
                )
                chat.value?.lastMessage?.lastMessage?.let { lastMessage ->
                    Text(
                        text = lastMessage,
                        color = Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}