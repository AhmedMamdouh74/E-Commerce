package com.example.data

import android.util.Log
import com.example.data.model.BaseResponse
import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.ParsingException
import com.example.domain.exceptions.ServerError
import com.example.domain.exceptions.ServerTimeOutException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException


suspend fun <T> safeApiCall(apiCall: suspend  () ->  BaseResponse<T>): Flow<ResultWrapper<T>> =

    flow {
        emit(ResultWrapper.Loading)
        val result = apiCall.invoke()
        result.data?.let {
            emit(ResultWrapper.Success(result.data))
            println("$TAG  ${result.data}")
        }

    }.catch { e ->
        Log.d(TAG, "$e ")
        when (e) {
            is TimeoutException -> {
                emit(ResultWrapper.Error(ServerTimeOutException(e)))
            }

            is IOException -> {
                emit(ResultWrapper.Error(ServerTimeOutException(e)))
            }

            is HttpException -> {
                val body = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(body, BaseResponse::class.java)
                emit(
                    ResultWrapper.ServerError(
                        ServerError(response.statusMessage ?: "", response.message ?: "", e.code())
                    )
                )
            }

            is JsonSyntaxException -> {
                emit(ResultWrapper.Error(ParsingException(e)))
            }

            else -> emit(ResultWrapper.Error(e as Exception))
        }
    }
const val TAG="safeApiCall"

