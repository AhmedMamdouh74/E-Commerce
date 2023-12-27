package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse

interface LoginDataSourceContract {
    suspend fun login(loginRequest: LoginRequest):ResultWrapper<LoginResponse?>
}