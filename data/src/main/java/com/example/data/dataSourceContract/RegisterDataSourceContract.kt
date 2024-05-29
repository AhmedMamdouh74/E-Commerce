package com.example.data.datasourcecontract

import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse

interface RegisterDataSourceContract {
    suspend fun register(registerRequest: RegisterRequest):RegisterResponse?
}