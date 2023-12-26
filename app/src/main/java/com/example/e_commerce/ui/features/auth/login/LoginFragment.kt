//package com.example.e_commerce.ui.features.auth.login
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import com.example.e_commerce.R
//import com.example.e_commerce.databinding.FragmentLoginBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class LoginFragment : Fragment() {
//    private var viewModel:LoginViewModel?=null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel=ViewModelProvider(this)[LoginViewModel::class.java]
//
//
//    }
//
//    private var viewBinding: FragmentLoginBinding? = null
//    private val binding get() = viewBinding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        viewBinding = null
//
//    }
//}