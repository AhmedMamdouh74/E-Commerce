package com.example.data.model.product

import com.example.data.model.BaseResponse
import com.example.data.model.category.CategoryDto
import com.example.data.model.subCategories.SubCategoriesDto
import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,
    @field:SerializedName("results")
    val results: Int? = null
) : BaseResponse<List<ProductDto?>?>()

data class ProductDto(

    @field:SerializedName("sold")
    val sold: Int? = null,

    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("availableColors")
    val availableColors: List<Any?>? = null,

    @field:SerializedName("imageCover")
    val imageCover: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("ratingsQuantity")
    val ratingsQuantity: Int? = null,

    @field:SerializedName("ratingsAverage")
    val ratingsAverage: Any? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("subcategory")
    val subcategory: List<SubCategoriesDto?>? = null,

    @field:SerializedName("category")
    val category: CategoryDto? = null,

    @field:SerializedName("brand")
    val brand: Any? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("priceAfterDiscount")
    val priceAfterDiscount: Int? = null,

)

data class Metadata(

    @field:SerializedName("numberOfPages")
    val numberOfPages: Int? = null,

    @field:SerializedName("nextPage")
    val nextPage: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("currentPage")
    val currentPage: Int? = null
)


