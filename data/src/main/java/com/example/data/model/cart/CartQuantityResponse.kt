package com.example.data.model.cart

import com.example.data.model.BaseResponse
import com.example.data.model.product.ProductDto

import com.google.gson.annotations.SerializedName

data class CartQuantityResponseDto(
    @field:SerializedName("numOfCartItems")
    val numOfCartItems: Int? = null,

    @field:SerializedName("status")
    val status: String? = null
) : BaseResponse<CartQuantityDto>()


data class CartQuantityDto(

    @field:SerializedName("cartOwner")
    val cartOwner: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("totalCartPrice")
    val totalCartPrice: Int? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("products")
    val products: List<ProductItemDto?>? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class ProductItemDto(

    @field:SerializedName("product")
    val product: ProductDto? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null
)
