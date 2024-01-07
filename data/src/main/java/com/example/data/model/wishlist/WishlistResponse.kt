package com.example.data.model.wishlist

import com.example.data.model.BaseResponse
import com.example.data.model.product.ProductDto
import com.google.gson.annotations.SerializedName

data class WishlistResponse(

    @field:SerializedName("count")
	val count: Int? = null,

    @field:SerializedName("status")
	val status: String? = null
):BaseResponse<List<ProductDto?>?>()




