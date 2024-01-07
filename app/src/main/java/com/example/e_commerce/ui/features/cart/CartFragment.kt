package com.example.e_commerce.ui.features.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Product
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCartBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var viewModel: CartViewModel
    private val tokenViewModel:TokenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    private var viewBinding: FragmentCartBinding? = null
    private val binding get() = viewBinding!!
    private val cartAdapter = CartAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.state.observe(viewLifecycleOwner, ::renderState)
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(CartContract.Action.LoadingLoggedUserCarts)

    }

    private fun initViews() {
        binding.cartRecycler.adapter = cartAdapter

    }

    private fun handleEvents(event: CartContract.Event?) {
        when (event) {
            else -> {}
        }


    }

    private fun renderState(state: CartContract.State?) {
        when (state) {
            is CartContract.State.Error -> showError(state.message)
            is CartContract.State.Loading -> showLoading(state.message)
            is CartContract.State.Success -> bindsCarts(state.cart?.products?.toMutableList())
            CartContract.State.Idle -> showIdle()
            null -> TODO()
            else -> {}
        }

    }

    private fun showIdle() {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true

    }

    private fun showLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text = message

    }

    private fun showError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            viewModel.handleAction(CartContract.Action.LoadingLoggedUserCarts)
        }
    }

    private fun bindsCarts(product:MutableList<ProductsItem?>?) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        cartAdapter.bindProducts(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}