package com.example.e_commerce.ui.spalsh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.data.api.TokenManager
import com.example.e_commerce.R
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.auth.register.RegisterFragment
import com.example.e_commerce.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            // navigateToHome()
             navigateToRegister()
        }, 2000)
        var tokenManager = TokenViewModel(TokenManager(this))
        val token = tokenManager.token.value
        Log.d("onCreateSplash: ", "$token")

    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToRegister() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.register_login_container, RegisterFragment())
            .commit()
    }
}