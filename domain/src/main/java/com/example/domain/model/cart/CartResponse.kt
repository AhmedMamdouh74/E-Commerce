package com.example.domain.model.cart

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.domain.model.Product

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
	val products: List<Product?>? = null,
	val updatedAt: String? = null
) : Parcelable




