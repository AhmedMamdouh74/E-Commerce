package com.example.e_commerce.ui.features.auth.register

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:Error")
fun bindingErrorOnTextInputLayout(
    textInputLayout: TextInputLayout,
    errorMessage: String?
) {
    textInputLayout.error=errorMessage

}