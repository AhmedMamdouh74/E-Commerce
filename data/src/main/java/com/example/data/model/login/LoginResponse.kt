package com.example.data.model.login


import com.example.data.model.register.UserDto
import com.google.gson.annotations.SerializedName

data  class LoginResponseDto(
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("user")
	val user: UserDto?=null,
	@field:SerializedName("token")
	val token:String?=null

)
