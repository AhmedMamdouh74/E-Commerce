package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface RegisterDataSourceContract {
    suspend fun register(registerRequest: RegisterRequest):RegisterResponse?
}