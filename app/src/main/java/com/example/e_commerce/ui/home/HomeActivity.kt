package com.example.e_commerce.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.api.TokenManager
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityHomeBinding
import com.example.e_commerce.ui.features.auth.AuthActivity
import com.example.e_commerce.ui.home.categories.CategoriesFragment
import com.example.e_commerce.ui.home.home.HomeFragment
import com.example.e_commerce.ui.home.profile.ProfileFragment
import com.example.e_commerce.ui.home.wishlist.WishlistFragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var viewBinding: ActivityHomeBinding
    val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        if (!isUserLogged(tokenManager)) {
            goToAuthActivity()

        }
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        // Initialize the Navigation controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Connect the BottomNavigationView
        viewBinding.bottomNavigation.setupWithNavController(navController)
    }



    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            setTheme(R.style.Theme_ECommerce)
        }
    }

    private fun isUserLogged(tokenManager: TokenManager): Boolean {
        val userToken = tokenManager.getToken()
        Log.d("TAG", "isUserLogged: $userToken")
        return !userToken.isNullOrEmpty()
    }

    private fun goToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val options = ActivityOptions.makeCustomAnimation(
            this, android.R.anim.fade_in, android.R.anim.fade_out
        )
        startActivity(intent, options.toBundle())
        finish()

    }

}