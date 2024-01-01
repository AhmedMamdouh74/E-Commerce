package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CartResponse(
	val data: Cart? = null,
	val numOfCartItems: Int? = null,
	val status: String? = null
) : Parcelable


@Parcelize
data class Cart(
	val cartOwner: String? = null,
	val createdAt: String? = null,
	val totalCartPrice: Int? = null,
	val __v: Int? = null,
	val _id: String? = null,
	val products: List<ProductsItem?>? = null,
	val updatedAt: String? = null
) : Parcelable



@Parcelize
data class ProductsItem(
	val product: Product? = null,
	val price: Int? = null,
	val count: Int? = null,
	val _id: String? = null
) : Parcelable
