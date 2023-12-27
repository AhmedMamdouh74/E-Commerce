package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse

interface RegisterDataSourceContract {
    suspend fun register(registerRequest: RegisterRequest):ResultWrapper<RegisterResponse?>
}