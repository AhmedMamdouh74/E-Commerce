package com.example.data.repository.auth

import com.example.data.datasourcecontract.AuthDataSourceContract
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSourceContract) :
    AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return authDataSource.login(loginRequest)
    }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return authDataSource.register(registerRequest)
    }
}