package com.example.chatlek.firebase.storage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageViewModel : ViewModel() {
    private val firebaseStorage = Firebase.storage.reference
    private val _imageData = MutableLiveData<ImageData>()
    val imageData: LiveData<ImageData> = _imageData

    fun uploadImage(uri: Uri) {
        val fileName = UUID.randomUUID().toString()
        val fileLocation = firebaseStorage.child(fileName)

        viewModelScope.launch {
            try {
                fileLocation.putFile(uri).await()
                val fileURL = fileLocation.downloadUrl.await().toString()
                val uploadImage = ImageData(fileName, fileURL)
                _imageData.value = uploadImage
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}