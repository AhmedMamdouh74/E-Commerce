package com.example.e_commerce.ui.home

import android.view.View
import androidx.core.content.ContextCompat
import com.example.e_commerce.R
import com.google.android.material.snackbar.Snackbar


fun View.showSnakeBarError(message: String):Snackbar {
   return Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(this.context.resources.getString(R.string.ok)) {}.setActionTextColor(
            ContextCompat.getColor(this.context, R.color.white)
        ).apply { show() }
}

fun View.showRetrySnakeBarError(message: String, retry: () -> Unit):Snackbar {
  return  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(this.context.resources.getString(R.string.retry)) { retry.invoke() }
        .setActionTextColor(
            ContextCompat.getColor(this.context, R.color.white)
        ).apply { show() }
}
