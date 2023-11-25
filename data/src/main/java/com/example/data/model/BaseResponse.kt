package com.example.data.model

import com.google.gson.annotations.SerializedName

 open class BaseResponse<T>(
    @field:SerializedName("statusMsg")
    val statusMessage: String?=null,
    @field:SerializedName("message")
    val message: String?=null,
    @field:SerializedName("data")
    val data:T?=null
) {
}