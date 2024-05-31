package com.example.e_commerce.ui.features.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {


    fun getToken(): String? {
        return tokenManager.getToken()
    }

    fun saveToken(token: String, user: User) {
        tokenManager.saveToken(token, user)
    }

    fun deleteToken() {
        tokenManager.deleteToken()
    }

    fun getUser(): User? {
        return tokenManager.getUser()
    }

}