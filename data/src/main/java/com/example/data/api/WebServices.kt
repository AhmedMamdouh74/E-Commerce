package com.example.data.api

import com.example.data.model.category.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query


//  The interface is used to make network requests to the news API.
interface WebServices {
    @GET("api/v1/categories")
    suspend fun getCategories(@Query("page") page: Int = 1): CategoryResponse

}
