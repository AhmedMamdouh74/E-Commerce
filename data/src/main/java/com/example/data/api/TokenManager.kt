package com.example.data.api

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
    }


    fun getToken(): String? {
        return sharedPreferences.getString("TOKEN_KEY", "token")
    }

    fun saveToken(token: String, user: User) {
        val userJson = Gson().toJson(user)

        sharedPreferences.edit().putString("TOKEN_KEY", token).putString("userJson", userJson)
            .apply()
    }

    fun getUser(): User? {
        val gson = Gson()
        val user = sharedPreferences.getString("userJson", "")
        return try {
            gson.fromJson(user, User::class.java)
        } catch (e: Exception) {
            null
        }

    }

    fun deleteToken() {
        sharedPreferences.edit().putString("TOKEN_KEY", "").apply()

    }
}