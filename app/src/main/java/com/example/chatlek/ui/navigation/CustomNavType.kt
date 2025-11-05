package com.example.chatlek.ui.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.example.chatlek.data.entity.user.GetUser
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val ChatType = object : NavType<GetUser>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: SavedState, key: String, value: GetUser) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(bundle: SavedState, key: String): GetUser? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun serializeAsValue(value: GetUser): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): GetUser {
            return Json.decodeFromString(Uri.decode(value))
        }

    }
}