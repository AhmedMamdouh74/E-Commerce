package com.example.data.model.subCategories

import com.example.data.model.BaseResponse
import com.example.domain.model.SubCategories
import com.google.gson.annotations.SerializedName

data class SubCategoriesResponse(

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,
	@field:SerializedName("results")
	val results: Int? = null
):BaseResponse<List<SubCategoriesDto>>()

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

data class SubCategoriesDto(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
){
	fun toSubCategory(): SubCategories {
		return SubCategories(
			name = name,
			id = id,
			category = category,
			slug = slug
		)
	}
}
