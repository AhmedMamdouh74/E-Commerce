package com.example.data.model.category

import com.example.data.model.BaseResponse
import com.example.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,
    @field:SerializedName("results")
    val results: Int? = null
) : BaseResponse<List<CategoryDto?>?>()

data class Metadata(

    @field:SerializedName("numberOfPages")
    val numberOfPages: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("currentPage")
    val currentPage: Int? = null
)

data class CategoryDto(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) {
//    fun ToCategory(): Category {
//        return Category(
//            image = image,
//            name = name,
//            _id = id,
//            slug = slug
//        )
//    }
}
