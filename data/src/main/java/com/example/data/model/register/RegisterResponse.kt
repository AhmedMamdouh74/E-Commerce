package com.example.data.model.register

import com.example.data.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("token")
	val token: String? = null
):BaseResponse<UserDto>()

data class UserDto(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
