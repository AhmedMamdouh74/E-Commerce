package com.example.e_commerce.ui.features.cart

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.cart.loggedCart.CartQuantity
import com.example.domain.model.cart.loggedCart.ProductsItem
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityCartBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private val viewModel: CartViewModel by viewModels()
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(this) }
    private val cartAdapter = CartAdapter()
    private lateinit var viewBinding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
//        viewBinding = ActivityCartBinding.inflate(layoutInflater)
//        setContentView(viewBinding.root)
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderState(it) }
            }
        }

        viewModel.event.observe(this, ::handleEvents)
        viewModel.handleAction(CartContract.Action.LoadingLoggedUserCarts)


    }

    private fun initViews() {
        lifecycleScope.launch {

            cartAdapter.onItemClickListener = CartAdapter.OnItemClickListener { _, item ->

                item.let {
                    viewModel.handleAction(
                        CartContract.Action.RemoveProductFromCart(
                            viewModel.token,
                            it?.product?.id ?: ""
                        )
                    )


                    cartAdapter.cartProductDeleted(it)
                    Snackbar.make(viewBinding.root, "Item Cart Deleted", Snackbar.LENGTH_SHORT)
                        .show()

                }
            }
            viewBinding.cartRecycler.adapter = cartAdapter
            viewBinding.icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    private fun handleEvents(event: CartContract.Event?) = when (event) {
        else -> {}
    }

    private fun renderState(state: CartContract.State?) {
        when (state) {
            is CartContract.State.Error -> showError(state.message)
            is CartContract.State.Loading -> showLoading()
            is CartContract.State.Success -> {
                bindsCarts(
                    state.cart?.products?.toMutableList(),
                    state.cart
                )
            }

            CartContract.State.Idle -> showIdle()

            else -> {}
        }

    }

    private fun showIdle() {
        progressDialog.dismiss()
        viewBinding.successView.isVisible = true

    }

    private fun showLoading() {
        progressDialog.show()
        viewBinding.successView.isVisible = true


    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        viewBinding.successView.isVisible = true
        viewBinding.root.showRetrySnakeBarError(message) {
            viewModel.handleAction(CartContract.Action.LoadingLoggedUserCarts)
        }
    }

    private fun bindsCarts(product: MutableList<ProductsItem?>?, cartQuantity: CartQuantity?) {
        progressDialog.dismiss()
        viewBinding.successView.isVisible = true
        cartAdapter.bindProducts(product)
        viewBinding.cart = cartQuantity
    }


}