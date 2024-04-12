package com.example.data.repository.login

import com.example.data.datasourcecontract.LoginDataSourceContract
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginDataSource: LoginDataSourceContract):LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return loginDataSource.login(loginRequest)
    }
}