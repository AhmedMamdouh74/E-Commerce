package com.example.data

import android.util.Log
import com.example.data.model.BaseResponse
import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.ParsingException
import com.example.domain.exceptions.ServerError
import com.example.domain.exceptions.ServerTimeOutException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import kotlin.math.log

suspend fun <T> saveApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    try {
        val result = apiCall.invoke()
        println("ahmed${result}")

        return ResultWrapper.Success(result)
    } catch (e: Exception) {
        println("ahmedError${e}")

        when (e) {
            is TimeoutException -> {
                return ResultWrapper.Error(e)
            }

            is IOException -> {
                return ResultWrapper.Error(ServerTimeOutException(e))
            }

            is HttpException -> {
                val body = e.response()?.errorBody()?.string()
                // baseResponse is not the same response from all API
                val response = Gson().fromJson(body, BaseResponse::class.java)
                return ResultWrapper.ServerError(
                    ServerError(
                             response.message ?: "",
                                response.statusMessage ?: "",
                       // body ?: "",
                       // body ?: "",
                        e.code()
                    )
                )
            }

            is JsonSyntaxException -> {
                return ResultWrapper.Error(ParsingException(e))
            }

            else -> return ResultWrapper.Error(e)


        }

    }


}