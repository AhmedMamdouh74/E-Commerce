package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.repositories.LoginRepository
import javax.inject.Inject

class GetLoginUseCases @Inject constructor (private val loginRepository: LoginRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest):ResultWrapper<LoginResponse?>{
        return loginRepository.login(loginRequest)
    }
}