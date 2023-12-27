package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginResponse(
    val message: String? = null,
    val user: User? = null,
    val token: String? = null
) : Parcelable

@Parcelize
data class User(
    val role: String? = null,
    val name: String? = null,
    val email: String? = null
) : Parcelable

data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)
