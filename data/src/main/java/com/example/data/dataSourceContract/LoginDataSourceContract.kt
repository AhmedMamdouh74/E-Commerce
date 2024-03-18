package com.example.data.dataSourceContract

import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse

interface LoginDataSourceContract {
    suspend fun login(loginRequest: LoginRequest):LoginResponse?
}