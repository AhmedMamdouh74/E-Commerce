package com.example.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

fun <T> Any.convertTo(clazz: Class<T>): T {
    val json = Gson().toJson(this)
    return Gson().fromJson(json, clazz)
}

open class BaseResponse<T>(
    @field:SerializedName("statusMsg")
    val statusMessage: String? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("data")
    val data: T? = null
)
