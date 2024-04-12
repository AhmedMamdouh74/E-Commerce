package com.example.e_commerce.ui.common.customviews

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.e_commerce.R


class ProgressDialog {
    companion object {
        fun createProgressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }
    }
}