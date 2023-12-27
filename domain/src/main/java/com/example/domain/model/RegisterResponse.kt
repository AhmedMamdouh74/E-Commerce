package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class RegisterResponse(
    val message: String? = null,
    val user: User? = null,
    val token: String? = null
) : Parcelable

data class RegisterRequest(

    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val rePassword: String? = null,
    val phone: String? = null


)


