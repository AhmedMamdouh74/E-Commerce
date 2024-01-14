package com.example.e_commerce.ui.home.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.cart.CartFragment
import com.example.e_commerce.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    val tokenViewModel:TokenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var viewBinding:FragmentHomeBinding?=null
    private val binding  get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            tokenViewModel.deleteToken()
           val intent=Intent(requireActivity(),SplashActivity::class.java)
            startActivity(intent)
//            requireActivity()
//                .supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.register_login_container,LoginFragment())
//                .commit()

        }
        binding.icCart.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,CartFragment())
                .commit()
        }
    }



    }