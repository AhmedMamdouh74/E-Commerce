package com.example.domain.usecase


import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.domain.repositories.RegistrationRepository
import javax.inject.Inject

class GetRegisterUseCases @Inject constructor(private val registrationRepository: RegistrationRepository) {
    suspend operator fun invoke(request: RegisterRequest):RegisterResponse?{
       return registrationRepository.register(request)

    }
}