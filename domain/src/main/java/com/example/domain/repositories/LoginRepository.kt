package com.example.domain.repositories

import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest):LoginResponse?
}