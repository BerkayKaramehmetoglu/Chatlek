package com.example.chatlek.ui.screens.profile.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatlek.R
import com.example.chatlek.firebase.storage.StorageViewModel
import com.example.chatlek.ui.screens.profile.ProfileViewModel
import com.example.chatlek.ui.theme.Gray
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CardFilled(
    profileViewModel: ProfileViewModel,
    storageViewModel: StorageViewModel = hiltViewModel(),
) {
    val image by storageViewModel.imageData.observeAsState()
    val message by profileViewModel.message.collectAsState()
    val user by profileViewModel.user.observeAsState()

    var name by remember(user) {
        mutableStateOf(TextFieldValue(user?.name ?: ""))
    }

    var lastName by remember(user) {
        mutableStateOf(TextFieldValue(user?.lastName ?: ""))
    }

    var email by remember(user) {
        mutableStateOf(TextFieldValue(user?.email ?: ""))
    }

    val context = LocalContext.current

    if (message.isNotEmpty()) {
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Gray,
        ),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
        ) {

            UserProfile(profileViewModel = profileViewModel)

            FieldsText(
                value = name,
                onChange = { name = it },
                label = stringResource(R.string.name),
                keyboardType = KeyboardType.Text
            )

            FieldsText(
                value = lastName,
                onChange = { lastName = it },
                label = stringResource(R.string.lastname),
                keyboardType = KeyboardType.Text
            )

            FieldsText(
                value = email,
                onChange = { email = it },
                label = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
                enabled = false
            )
            val userState = user?.name.isNullOrEmpty() && user?.name.isNullOrEmpty()
            FilledButton(
                onClick = {
                    if (userState) {
                        profileViewModel.createUser(
                            name = name.text,
                            lastName = lastName.text,
                            picURL = image?.imageURL ?: ""
                        )
                    } else {
                        profileViewModel.updateUser(
                            name = name.text,
                            lastName = lastName.text,
                            picURL = image?.imageURL ?: user!!.profilePic,
                        )
                    }
                },
                string = if (userState) stringResource(R.string.create) else stringResource(R.string.update)
            )
        }
    }
}