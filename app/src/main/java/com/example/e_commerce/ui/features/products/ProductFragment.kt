package com.example.e_commerce.ui.features.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.e_commerce.databinding.FragmentProductBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.products.details.ProductDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {
    lateinit var category: Category
    private lateinit var viewModel: ProductsViewModel
    private val tokenViewModel: TokenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
    }

    private var viewBinding: FragmentProductBinding? = null
    private val binding get() = viewBinding!!
    private val productsAdapter = ProductsAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initViews() {
        productsAdapter.onItemClickListener =
            ProductsAdapter.OnItemClickListener { position, item ->
                item?.let {
                    viewModel.handleAction(
                        ProductContract.Action.ProductsClicked(
                            it
                        )
                    )
                }
                Log.d("TAG", "initViewsAndroid: ")
            }
        productsAdapter.onIconWishlistClickListener =
            ProductsAdapter.OnItemClickListener { position, item ->
                item?.let {
                    if (it.isAdded == true) {
                        viewModel.handleAction(
                            ProductContract.Action.RemoveProductToWishlist(
                                it.id ?: "", tokenViewModel.getToken()
                            )
                        )
                    } else {
                        viewModel.handleAction(
                            ProductContract.Action.AddProductToWishlist(
                                it.id ?: "",
                                tokenViewModel.getToken()
                            )
                        )
                        Log.d("TAG", "initViews:${tokenViewModel.getToken()} ")
                        Log.d("TAG", "initViews:${it.id} ")

                    }

                }
            }


        binding.productsRecycler.adapter = productsAdapter
    }

    private fun handleEvents(event: ProductContract.Event?) {
        when (event) {
            is ProductContract.Event.NavigateToProductsDetails -> navigateToProductsDetails(event.product)

            is ProductContract.Event.NavigateToCartScreen -> {}

            else -> {}
        }
    }

    private fun navigateToProductsDetails(product: Product) {
        val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
        Log.d("TAG", "navigateToProductsDetails: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wishlistState.collect { renderWishlistState(it) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loggedWishlistState.collect { renderLoggedWishlistState(it) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderViewStates(it) }

            }
        }
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(ProductContract.Action.LoadingProducts(category._id ?: ""))
    }



    private fun renderWishlistState(wishlistState: ProductContract.WishlistState?) {
        when (wishlistState) {
            is ProductContract.WishlistState.Error -> {}
            is ProductContract.WishlistState.Loading -> {}
            is ProductContract.WishlistState.Success -> {
                viewModel.getLoggedWishlist()
                Log.d("TAG", "renderWishlistStateAhmed:$wishlistState ")
            }


            else -> {}
        }
    }

    private fun renderLoggedWishlistState(loggedWishlistState: ProductContract.LoggedWishlistState?) {
        when (loggedWishlistState) {
            is ProductContract.LoggedWishlistState.Error -> {}
            is ProductContract.LoggedWishlistState.Loading -> {}
            is ProductContract.LoggedWishlistState.Success -> productsAdapter.setWishlist(
                loggedWishlistState.wishlistProduct
            )

            else -> {}
        }
    }

    private fun renderViewStates(state: ProductContract.State?) {
        when (state) {
            is ProductContract.State.Error -> showError(state.message)
            is ProductContract.State.Loading -> showLoading(state.message)
            is ProductContract.State.Success -> bindsProducts(state.product)


            else -> {}
        }
    }

    private fun bindsProducts(product: List<Product?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        productsAdapter.bindProducts(product)
    }

    private fun showLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text = message
    }

    private fun showError(message: String) {
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            viewModel.handleAction(ProductContract.Action.LoadingProducts(category._id ?: ""))
        }
    }

    companion object {
        fun getInstance(category: Category): ProductFragment {
            val productFragmentRef = ProductFragment()
            productFragmentRef.category = category
            return productFragmentRef
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}