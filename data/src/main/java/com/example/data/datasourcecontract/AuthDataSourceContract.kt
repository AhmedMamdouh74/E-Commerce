package com.example.data.datasourcecontract

import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse

interface AuthDataSourceContract {
    suspend fun login(loginRequest: LoginRequest): LoginResponse?
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse?
}