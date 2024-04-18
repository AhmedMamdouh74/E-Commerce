package com.example.domain.common


sealed class ResultWrapper<out T>{
    data class ServerError(val error:com.example.domain.exceptions.ServerError):ResultWrapper<Nothing>()
    data class Error(val error:Exception):ResultWrapper<Nothing>()
    data class Success<T>(val data:T):ResultWrapper<T>()
    object Loading:ResultWrapper<Nothing>()


}
