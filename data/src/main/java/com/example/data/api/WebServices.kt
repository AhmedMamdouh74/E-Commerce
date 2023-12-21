package com.example.data.api

import com.example.data.model.BaseResponse
import com.example.domain.model.Category
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.Product
import com.example.domain.model.RegisterRequest
import com.example.domain.model.RegisterResponse
import com.example.domain.model.SubCategories
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("auth/refreshToken")
    fun refreshToken(oldToken: String): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun register(@Body registerRequest: RegisterRequest): BaseResponse<RegisterResponse?>

    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): BaseResponse<LoginResponse?>


}
