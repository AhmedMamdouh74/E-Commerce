package com.example.e_commerce.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityHomeBinding
import com.example.e_commerce.ui.home.categories.CategoriesFragment
import com.example.e_commerce.ui.home.home.HomeFragment
import com.example.e_commerce.ui.home.profile.ProfileFragment
import com.example.e_commerce.ui.home.wishlist.WishlistFragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var viewBinding: ActivityHomeBinding
    val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewBinding.bottomNavigation.setOnItemSelectedListener(this)
        viewBinding.bottomNavigation.selectedItemId=R.id.navigation_category
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                pushFragment(HomeFragment())
            }

            R.id.navigation_category -> {
                pushFragment(CategoriesFragment())
            }

            R.id.navigation_profile -> {
                pushFragment(ProfileFragment())
            }

            R.id.navigation_wishlist -> {
                pushFragment(WishlistFragment())
            }
        }
        return true
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }
}