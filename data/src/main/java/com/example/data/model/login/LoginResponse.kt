package com.example.data.model.login

import com.example.data.model.BaseResponse
import com.example.data.model.register.UserDto
import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("token")
	val token: String? = null
):BaseResponse<UserDto>()
