package com.example.e_commerce.ui.features.auth


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.TokenManager
import com.example.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {


    fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        tokenManager.getToken().toString()
    }


    fun saveToken(token: String, user: User) = viewModelScope.launch(Dispatchers.IO) {
        tokenManager.saveToken(token, user)
    }


    fun deleteToken() = viewModelScope.launch(Dispatchers.IO) {
        tokenManager.deleteToken()
    }

}