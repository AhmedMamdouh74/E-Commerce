package com.example.data.repository.register

import com.example.data.datasource.register.RegisterDataSourceImpl
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.domain.repositories.RegistrationRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerDataSourceImpl: RegisterDataSourceImpl):RegistrationRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse?{
        return registerDataSourceImpl.register(registerRequest)
    }
}