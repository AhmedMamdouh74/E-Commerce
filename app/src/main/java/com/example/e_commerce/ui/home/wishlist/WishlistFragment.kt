package com.example.e_commerce.ui.home.wishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.Product
import com.example.e_commerce.databinding.FragmentWishlistBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.cart.CartActivity
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import com.example.e_commerce.ui.home.showSnakeBarError
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private lateinit var viewModel: WishlistViewModel

    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireActivity()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WishlistViewModel::class.java]
    }

    private val wishlistAdapter = WishlistAdapter()
    private var _viewBinding: FragmentWishlistBinding? = null

    private val binding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun handleEvents(event: WishlistContract.Event) {
        when (event) {
            WishlistContract.Event.NavigateToCartScreen -> navigateToCartScreen()
            else -> {}
        }


    }

    private fun navigateToCartScreen() {
        startActivity(Intent(requireActivity(), CartActivity::class.java))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartState.collect { renderCartState(it) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loggedUserCartState.collect {
                    renderLoggedUserCartState(it)
                }

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { renderStates(it) }

            }
        }

        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(WishlistContract.Action.LoadingFavouriteProducts)
        binding.icCart.setOnClickListener {
            viewModel.handleAction(WishlistContract.Action.CartClicked)
        }
    }

    private fun renderCartState(cartState: WishlistContract.CartState?) {

        when (cartState) {
            is WishlistContract.CartState.Error -> {
                view?.showSnakeBarError(cartState.message)
            }

            is WishlistContract.CartState.Loading -> {}
            WishlistContract.CartState.Success -> viewModel.getLoggedUserCart(viewModel.token)
            else -> {}
        }
    }

    private fun renderLoggedUserCartState(loggedUserCartState: WishlistContract.LoggedUserCartState?) {
        when (loggedUserCartState) {
            is WishlistContract.LoggedUserCartState.Error -> {
                view?.showSnakeBarError(loggedUserCartState.message)
            }

            is WishlistContract.LoggedUserCartState.Loading -> {}
            is WishlistContract.LoggedUserCartState.Success -> {
                wishlistAdapter.setCart(
                    loggedUserCartState.cart?.products?.toMutableList()

                )
            }


            else -> {}
        }


    }

    private fun renderStates(state: WishlistContract.State) {
        when (state) {
            is WishlistContract.State.Error -> {
                viewError(state.message)
            }

            is WishlistContract.State.Loading -> {
                viewLoading()
            }

            is WishlistContract.State.Success -> {
                bindProducts(state.product)
            }

            is WishlistContract.State.Idle -> showIdle()
            else -> {}
        }


    }

    private fun showIdle() {
        progressDialog.dismiss()
        binding.successView.isVisible = true
    }

    private fun bindProducts(product: List<Product?>) {
        progressDialog.dismiss()
        binding.successView.isVisible = true
        wishlistAdapter.bindProducts(product.toMutableList())


    }

    private fun viewError(message: String) {
        progressDialog.dismiss()
        binding.successView.isVisible = false
        view?.showRetrySnakeBarError(message) {
            viewModel.handleAction(WishlistContract.Action.LoadingFavouriteProducts)
        }

    }

    private fun viewLoading() {
        progressDialog.show()
        binding.successView.isVisible = true


    }

    private fun initViews() {
        binding.wishlistRecycler.adapter = wishlistAdapter
        wishlistAdapter.onItemClickListener =
            WishlistAdapter.OnItemClickListener { position, item ->
                item?.let {
                    viewModel.handleAction(
                        WishlistContract.Action.RemoveProductFromWishlist(
                            it.id ?: "", viewModel.token
                        )
                    )
                    wishlistAdapter.favouriteProductDeleted(it)

                    Snackbar.make(
                        binding.root,
                        "Item removed from Wishlist",
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            }

        wishlistAdapter.onItemAddedListener =
            WishlistAdapter.OnItemClickListener { position, item ->
                item?.let {

                    if (it.addedToCart == true) {
                        Snackbar.make(binding.root, "Added To Cart Already", Snackbar.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.handleAction(
                            WishlistContract.Action.AddProductToCart(
                                viewModel.token,
                                it.id ?: ""
                            )
                        )


                    }


                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}