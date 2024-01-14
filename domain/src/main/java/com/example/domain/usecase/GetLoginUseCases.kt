package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginUseCases @Inject constructor (private val loginRepository: LoginRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest):LoginResponse?{
        return loginRepository.login(loginRequest)
    }
}