package com.example.e_commerce.ui.features.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private val viewModel: CartViewModel by viewModels()
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(this) }
    private val cartAdapter = CartAdapter(null)
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
        cartAdapter.onItemClickListener = CartAdapter.OnItemClickListener { _, item ->
            item.let {
                viewModel.handleAction(
                    CartContract.Action.RemoveProductFromCart(
                        viewModel.token,
                        it?.product?.id ?: ""
                    )
                )
                cartAdapter.cartProductDeleted(it)
                Snackbar.make(viewBinding.root, "Item Cart Deleted", Toast.LENGTH_LONG).show()

            }
        }
        viewBinding.cartRecycler.adapter = cartAdapter
        viewBinding.icBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun handleEvents(event: CartContract.Event?) = when (event) {
        else -> {}
    }

    private fun renderState(state: CartContract.State?) {
        when (state) {
            is CartContract.State.Error -> showError(state.message)
            is CartContract.State.Loading -> showLoading()
            is CartContract.State.Success -> bindsCarts(
                state.cart?.products?.toMutableList(),
                state.cart
            )

            CartContract.State.Idle -> showIdle()

            else -> {}
        }

    }

    private fun showIdle() {
        progressDialog.dismiss()
        viewBinding.errorView.isVisible = false
        viewBinding.successView.isVisible = true

    }

    private fun showLoading() {
        progressDialog.show()
        viewBinding.errorView.isVisible = false
        viewBinding.successView.isVisible = false


    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        viewBinding.errorView.isVisible = true
        viewBinding.successView.isVisible = false
        viewBinding.errorText.text = message
        viewBinding.btnTryAgain.setOnClickListener {
            viewModel.handleAction(CartContract.Action.LoadingLoggedUserCarts)
        }
    }

    private fun bindsCarts(product: MutableList<ProductsItem?>?, cartQuantity: CartQuantity?) {
        progressDialog.dismiss()
        viewBinding.errorView.isVisible = false
        viewBinding.successView.isVisible = true
        cartAdapter.bindProducts(product!!)
        viewBinding.cart = cartQuantity
    }
}