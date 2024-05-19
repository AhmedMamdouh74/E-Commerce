package com.example.data.api

import com.example.data.model.BaseResponse
import com.example.data.model.login.LoginResponseDto
import com.example.data.model.register.RegisterResponseDto
import com.example.domain.model.Category
import com.example.domain.model.LoginRequest
import com.example.domain.model.LoginResponse
import com.example.domain.model.Product
import com.example.domain.model.RegisterRequest
import com.example.domain.model.SubCategories
import com.example.domain.model.wishlist.WishlistResponse
import com.example.domain.model.cart.CartResponse
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.model.wishlist.AddToWishlistResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponseDto?

    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponseDto?

    @POST("wishlist")
    @FormUrlEncoded
    suspend fun addProductToWishlist(
        @Header("token") token: String,
        @Field("productId") productId: String
    ): BaseResponse<List<String?>?>

    @DELETE("wishlist/{productId}")
    suspend fun removeProductFromWishlist(
        @Path("productId") productId: String,
        @Header("token") token: String
    ): BaseResponse<Any>

    @GET("wishlist")
    suspend fun getLoggedUserWishlist(@Header("token") token: String): BaseResponse<List<Product?>?>

    @POST("cart")
    @FormUrlEncoded
    suspend fun addProductToCart(
        @Header("token") token: String,
        @Field("productId") productId: String
    ): BaseResponse<CartResponse?>

    @DELETE("cart/{productId}")
    suspend fun removeProductFromCart(
        @Header("token") token: String,
        @Path("productId") productId: String
    ): BaseResponse<Any>

    @GET("cart")
    suspend fun getLoggedUserCart(
        @Header("token") token: String
    ): BaseResponse<CartQuantity?>



}
