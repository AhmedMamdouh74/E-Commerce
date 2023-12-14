package com.example.data.api

import com.example.data.model.BaseResponse
import com.example.data.model.category.CategoryResponse
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SubCategories
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//  The interface is used to make network requests to the news API.
interface WebServices {
    @GET("categories")
    suspend fun getCategories(@Query("page") page: Int = 1): BaseResponse<List<Category?>?>

    @GET("categories/{categoryId}/subcategories")
    suspend fun getSubCategories(
        @Path("categoryId") id: String
    ): BaseResponse<List<SubCategories?>?>

    @GET("products")
    suspend fun getProducts(
        @Query("category[in]") id: String
        //  @Query("page") page:Int
    ): BaseResponse<List<Product?>?>

    @GET("products/{productsId}")
    suspend fun getSpecificProducts(
        @Path("productsId") productId: String
    ): BaseResponse<Product?>



}
