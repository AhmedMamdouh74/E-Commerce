package com.example.domain.repositories

import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse?
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse?
}