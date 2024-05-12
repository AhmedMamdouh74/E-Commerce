package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable



@Parcelize
data class Product(
	val sold: Int? = null,
	val images: List<String?>? = null,
	val quantity: Int? = null,
	val imageCover: String? = null,
	val description: String? = null,
	val title: String? = null,
	val ratingsQuantity: Int? = null,
	val ratingsAverage: Double? = null,
	val createdAt: String? = null,
	val price: Int? = null,
	val id: String? = null,
	val subcategory: List<SubCategories?>? = null,
	val category: Category? = null,
	val brand: Brand? = null,
	val slug: String? = null,
	val updatedAt: String? = null,
	val priceAfterDiscount: Int? = null,
	var isAdded:Boolean?=null,
	var addedToCart:Boolean?=false
) : Parcelable


