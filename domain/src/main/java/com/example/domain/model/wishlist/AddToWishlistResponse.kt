package com.example.domain.model.wishlist

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AddToWishlistResponse(
	val data: List<String?>? = null,
	val message: String? = null,
	val status: String? = null
) : Parcelable
