package com.example.domain.model.cart.loggedCart

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.domain.model.Product
@Parcelize
data class CartQuantityResponse(
	val data: CartQuantity? = null,
	val numOfCartItems: Int? = null,
	val status: String? = null
) : Parcelable

@Parcelize
data class CartQuantity(
    val cartOwner: String? = null,
    val createdAt: String? = null,
    val totalCartPrice: Int? = null,
    val __v: Int? = null,
    val _id: String? = null,
    var products: List<ProductsItem?>? = null,
    val updatedAt: String? = null
) : Parcelable

@Parcelize
data class ProductsItem(
	val product: Product? = null,
	val price: Int? = null,
	val count: Int? = null,
	val id: String? = null
) : Parcelable

