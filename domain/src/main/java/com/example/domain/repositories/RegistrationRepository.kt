package com.example.domain.repositories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun register(registerRequest: RegisterRequest):RegisterResponse?
}