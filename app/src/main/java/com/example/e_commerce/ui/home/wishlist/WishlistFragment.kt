package com.example.e_commerce.ui.home.wishlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.api.TokenManager
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentWishlistBinding
import com.example.e_commerce.ui.features.auth.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    val tokenViewModel: TokenViewModel by viewModels()
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
            WishlistContract.Event.NavigateToCartScreen -> {}
            else -> {}
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.cartState.observe(viewLifecycleOwner, ::renderCartState)
        viewModel.loggedUserCartState.observe(viewLifecycleOwner, ::renderLoggedUserCartState)
        viewModel.state.observe(viewLifecycleOwner, ::renderStates)
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(WishlistContract.Action.LoadingFavouriteProducts)
    }

    private fun renderCartState(cartState: WishlistContract.CartState?) {

        when (cartState) {
            is WishlistContract.CartState.Error -> {}
            is WishlistContract.CartState.Loading -> {}
            WishlistContract.CartState.Success -> viewModel.getLoggedUserCart(tokenViewModel.getToken())
            null -> {}
        }
    }

    private fun renderLoggedUserCartState(loggedUserCartState: WishlistContract.LoggedUserCartState?) {
        when (loggedUserCartState) {
            is WishlistContract.LoggedUserCartState.Error -> {}
            is WishlistContract.LoggedUserCartState.Loading -> {}
            is WishlistContract.LoggedUserCartState.Success -> wishlistAdapter.setCart(
                listOf(loggedUserCartState.cart[id]?.products?.get(id)?.product)
            )


            null -> {}
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
                            it.id ?: "", tokenViewModel.getToken()
                        )
                    )
                    wishlistAdapter.favouriteProductDeleted(it)
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
                                tokenViewModel.getToken(),
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