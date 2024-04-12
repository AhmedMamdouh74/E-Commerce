package com.example.e_commerce.ui.home.wishlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.Product
import com.example.e_commerce.databinding.FragmentWishlistBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import com.example.e_commerce.ui.features.cart.CartActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private val tokenViewModel: TokenViewModel by viewModels()
    private lateinit var viewModel: WishlistViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WishlistViewModel::class.java]
    }

    private val wishlistAdapter = WishlistAdapter(null)
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
        startActivity(Intent(requireActivity(),CartActivity::class.java))

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
            is WishlistContract.CartState.Error -> {}
            is WishlistContract.CartState.Loading -> {}
            WishlistContract.CartState.Success -> viewModel.getLoggedUserCart(viewModel.token)
            null -> {}
        }
    }

    private fun renderLoggedUserCartState(loggedUserCartState: WishlistContract.LoggedUserCartState?) {
        when (loggedUserCartState) {
            is WishlistContract.LoggedUserCartState.Error -> {}
            is WishlistContract.LoggedUserCartState.Loading -> {}
            is WishlistContract.LoggedUserCartState.Success ->
                wishlistAdapter.setCart(
                    loggedUserCartState.cart?.products?.toMutableList()

                )

            //  wishlistAdapter.setCart(loggedUserCartState.cart?.mapNotNull { it?.products?.get(0)?.product })

            else -> {}
        }


    }

    private fun renderStates(state: WishlistContract.State) {
        when (state) {
            is WishlistContract.State.Error -> {
                viewError(state.message)
            }

            is WishlistContract.State.Loading -> {
                viewLoading(state.message)
            }

            is WishlistContract.State.Success -> {
                bindProducts(state.product)
            }

            is WishlistContract.State.Idle -> showIdle()
            else -> {}
        }
        Log.d("TAG", "renderStatesWishlistFragment:$state ")

    }

    private fun showIdle() {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
    }

    private fun bindProducts(product: List<Product?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        wishlistAdapter.bindProducts(product.toMutableList())
        Log.d("TAG", "bindProducts:$product ")


    }

    private fun viewError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            viewModel.handleAction(WishlistContract.Action.LoadingFavouriteProducts)
        }

    }

    private fun viewLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text = message

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

                    Toast.makeText(
                        requireContext(),
                        "Item removed from Wishlist",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("TAG", "initViewsWishlist:$it ")
                }
            }

        wishlistAdapter.onItemAddedListener =
            WishlistAdapter.OnItemClickListener { position, item ->
                item?.let {

                    if (it.addedToCart == true) {
                        Toast.makeText(context, "Added To Cart Already", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.handleAction(
                            WishlistContract.Action.AddProductToCart(
                                viewModel.token,
                                it.id ?: ""
                            )
                        )


                    }
                    Log.d("TAG", "initViews:$it ")

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


}