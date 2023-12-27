package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WishlistResponse(
	val data: List<Product?>? = null,
	val count: Int? = null,
	val message:String?=null,
	val status: String? = null

) : Parcelable


