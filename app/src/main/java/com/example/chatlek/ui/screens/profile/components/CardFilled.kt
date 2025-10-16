package com.example.chatlek.ui.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatlek.R
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Gray
import com.example.chatlek.ui.theme.White


@Composable
fun CardFilled(
    name: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    lastName: TextFieldValue,
    onLastNameChange: (TextFieldValue) -> Unit,
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
) {

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

            Box(
                modifier = Modifier.size(146.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                        .background(Black_Out)
                )

                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .border(3.dp, White, CircleShape),
                    onClick = { /*do something */ }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.baseline_edit_24),
                        contentDescription = null,
                        tint = White
                    )
                }
            }

            FieldsText(
                value = name,
                onChange = onNameChange,
                label = stringResource(R.string.name),
                keyboardType = KeyboardType.Text
            )

            FieldsText(
                value = lastName,
                onChange = onLastNameChange,
                label = stringResource(R.string.lastname),
                keyboardType = KeyboardType.Text
            )

            FieldsText(
                value = email,
                onChange = onEmailChange,
                label = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
            )

            FilledButton(onClick = {/*do something */ }, string = stringResource(R.string.confirm))
        }
    }
}