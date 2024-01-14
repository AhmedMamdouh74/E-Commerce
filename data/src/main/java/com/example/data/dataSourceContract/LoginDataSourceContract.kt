package com.example.data.dataSourceContract

import com.example.data.model.login.LoginResponseDto
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginDataSourceContract {
    suspend fun login(loginRequest: LoginRequest):LoginResponse?
}