package com.example.data.datasource.auth

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.AuthDataSourceContract
import com.example.data.model.convertTo
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import javax.inject.Inject

class AuthDataSourceImpl@Inject constructor(private val webServices: WebServices):AuthDataSourceContract {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {

        return webServices.login(loginRequest)?.convertTo(LoginResponse::class.java)
    }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return webServices.register(registerRequest)?.convertTo(RegisterResponse::class.java)

    }
}