package com.example.chatlek.ui.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.chatlek.R
import com.example.chatlek.data.entity.user.GetUser
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    chatUser: GetUser,
    navHostController: NavHostController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Black_Out,
            titleContentColor = Black_Out,
            scrolledContainerColor = Black_Out
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = chatUser.profilePic,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Black_Out)
                        .size(56.dp)
                )
                Text(
                    text = "${chatUser.name.capitalize()} ${chatUser.lastName.capitalize()}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = White,
                    fontSize = 16.sp,
                )
            }
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.outline_arrow_back_24),
                    contentDescription = null,
                    tint = White
                )
            }
        }
    )
}
