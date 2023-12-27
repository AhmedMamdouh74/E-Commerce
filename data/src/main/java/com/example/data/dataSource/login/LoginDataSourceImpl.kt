package com.example.data.dataSource.login

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.LoginDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val webServices: WebServices):LoginDataSourceContract {
    override suspend fun login(loginRequest: LoginRequest): ResultWrapper<LoginResponse?> {

        return saveApiCall {
            webServices.login(loginRequest).body()
        }
    }
}