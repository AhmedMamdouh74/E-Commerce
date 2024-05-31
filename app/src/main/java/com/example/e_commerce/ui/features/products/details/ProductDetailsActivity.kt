package com.example.e_commerce.ui.features.products.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityProductDetailsBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.cart.CartActivity
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductDetailsViewModel by viewModels()
    lateinit var product: Product
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        getProduct()
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderViewStates(it) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderViewStates(it) }
            }
        }

        viewModel.event.observe(this, ::handleEvents)
        viewModel.handleAction(ProductsDetailsContract.Action.LoadingProduct(product.id ?: ""))

    }

    private fun renderViewStates(state: ProductsDetailsContract.State?) {
        when (state) {
            is ProductsDetailsContract.State.Error -> showError(state.message)
            is ProductsDetailsContract.State.Loading -> showLoading(state.message)
            is ProductsDetailsContract.State.Success -> bindProductDetail(state.product)

            else -> {}
        }

    }

    private fun handleEvents(event: ProductsDetailsContract.Event?) {
        when (event) {
            ProductsDetailsContract.Event.NavigateToCart -> navigateToCart()

            else -> {}
        }

    }

    private fun navigateToCart() {
        startActivity(Intent(this, CartActivity::class.java))

    }

    private fun bindProductDetail(product: Product?) {
        progressDialog.dismiss()
        binding.product = product
        Glide
            .with(this)
            .load(product?.imageCover)
            .placeholder(R.drawable.ic_download)
            .error(R.drawable.ic_error)
            .into(binding.productDetailsImage)
    }

    private fun showLoading(message: String) {
        progressDialog.show()
    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        binding.root.showRetrySnakeBarError(message) {
            viewModel.handleAction(ProductsDetailsContract.Action.LoadingProduct(product.id ?: ""))
        }
    }

    private fun initViews() {
        binding.icBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.icCart.setOnClickListener { viewModel.handleAction(ProductsDetailsContract.Action.ClickOnCartIcon) }
    }

    private fun getProduct() {
        product = intent.getParcelableExtra("product")!!

    }
}