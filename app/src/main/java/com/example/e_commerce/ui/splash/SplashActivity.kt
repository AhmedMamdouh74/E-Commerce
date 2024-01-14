package com.example.e_commerce.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.data.api.TokenManager
import com.example.e_commerce.R
import com.example.e_commerce.ui.features.auth.login.LoginFragment
import com.example.e_commerce.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isUserLogged(tokenManager)) {
                navigateToHome()

            } else
                navigateToLogin()


        }, 2000)


    }

    private fun isUserLogged(tokenManager: TokenManager): Boolean {
        val userToken = tokenManager.getToken()
        Log.d("TAG", "isUserLogged: $userToken")
        return !userToken.isNullOrEmpty()


    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun navigateToLogin() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.register_login_container, LoginFragment())
            .commit()
    }
}