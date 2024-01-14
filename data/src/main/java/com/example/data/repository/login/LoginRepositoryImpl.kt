package com.example.data.repository.login

import com.example.data.dataSource.login.LoginDataSourceImpl
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginDataSourceImpl: LoginDataSourceImpl):LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return loginDataSourceImpl.login(loginRequest)
    }
}