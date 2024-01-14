package com.example.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val userModel: UserDto? = null,

	@field:SerializedName("token")
	val token: String? = null,
	@field:SerializedName("statusMsg")
	val statusMsg : String?=null
)

data class UserDto(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
