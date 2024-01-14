package com.example.domain.model.wishlist

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.domain.model.Product

@Parcelize
data class WishlistResponse(
	val data: List<Product?>? = null,
	val count: Int? = null,
	val message:String?=null,
	val status: String? = null

) : Parcelable


