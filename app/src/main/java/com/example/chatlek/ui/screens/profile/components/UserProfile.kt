package com.example.chatlek.ui.screens.profile.components

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatlek.R
import com.example.chatlek.firebase.storage.StorageViewModel
import com.example.chatlek.ui.screens.profile.ProfileViewModel
import com.example.chatlek.ui.theme.Black_Out
import com.example.chatlek.ui.theme.Green

@Composable
fun UserProfile(profileViewModel: ProfileViewModel, storageViewModel: StorageViewModel) {

    val user by profileViewModel.user.observeAsState()
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            storageViewModel.uploadImage(imageUri!!)
        }
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
        }

    val galleryPermission =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) galleryLauncher.launch(input = "image/*")
            else Toast.makeText(context, "Gallery Permission Denied", Toast.LENGTH_SHORT).show()
        }

    Box(
        modifier = Modifier.size(146.dp)
    ) {
        val userProfile = user?.profilePic.isNullOrBlank()
        val imageURL = imageUri != null

        if (!userProfile || imageURL) {
            AsyncImage(
                model = if (imageURL) ImageRequest.Builder(context).data(imageUri).build()
                else user!!.profilePic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Black_Out)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Black_Out)
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .border(4.dp, Green, CircleShape),
            onClick = {
                galleryPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.baseline_edit_24),
                contentDescription = null,
                tint = Green
            )
        }
    }
}