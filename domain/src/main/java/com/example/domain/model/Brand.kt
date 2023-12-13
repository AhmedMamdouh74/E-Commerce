package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Brand(
    val image: String? = null,
    val createdAt: String? = null,
    val name: String? = null,
    val id: String? = null,
    val slug: String? = null,
    val updatedAt: String? = null
) : Parcelable
