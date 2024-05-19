package com.example.data.datasource.login

import com.example.data.api.WebServices
import com.example.data.datasourcecontract.LoginDataSourceContract
import com.example.data.model.convertTo
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val webServices: WebServices):LoginDataSourceContract {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse? {


        return webServices.login(loginRequest)?.convertTo(LoginResponse::class.java)

    }
}