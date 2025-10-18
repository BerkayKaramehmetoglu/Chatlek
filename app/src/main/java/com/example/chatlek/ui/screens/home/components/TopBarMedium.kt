package com.example.chatlek.ui.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatlek.R
import com.example.chatlek.firebase.auth.AuthViewModel
import com.example.chatlek.ui.navigation.Screen
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMedium(
    navHostController: NavHostController,
    authViewModel: AuthViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Black_Out,
            titleContentColor = Black_Out,
            scrolledContainerColor = Black_Out
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(R.font.bebasneue_regular)),
                fontSize = 48.sp,
                color = Gray,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.navigate(Screen.Profile.route) }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.outline_account_box_24),
                    contentDescription = null,
                    tint = Gray
                )
            }
        },
        actions = {
            IconButton(onClick = { authViewModel.signOut() }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.outline_exit_to_app_24),
                    contentDescription = null,
                    tint = Gray
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}