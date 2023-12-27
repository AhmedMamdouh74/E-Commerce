package com.example.data.dataSource.register

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.RegisterDataSourceContract
import com.example.data.saveApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(private val webServices: WebServices):RegisterDataSourceContract {
    override suspend fun register(registerRequest: RegisterRequest): ResultWrapper<RegisterResponse?> {
        return saveApiCall {

            webServices.register(registerRequest).body()
        }
    }
}