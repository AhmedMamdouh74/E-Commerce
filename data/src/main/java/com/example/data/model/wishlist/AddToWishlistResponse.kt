package com.example.data.model.wishlist

import com.example.data.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class AddToWishlistResponseDto(
	@field:SerializedName("status")
	val status: String? = null
):BaseResponse<String?>()
