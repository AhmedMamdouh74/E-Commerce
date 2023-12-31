package com.example.e_commerce.ui.home.wishlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.api.TokenManager
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    @Inject
    lateinit var tokenManager: TokenManager
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

        viewModel.state.observe(viewLifecycleOwner, ::renderStates)
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(WishlistContract.Action.LoadingFavouriteProducts)
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
        wishlistAdapter.bindProducts(product)
        Log.d("TAG", "bindProducts:$product ")
//        binding.wishlistRecycler.apply {
//            layoutManager =
//                LinearLayoutManager(requireContext()) // Use an appropriate layout manager
//            adapter = wishlistAdapter
//            visibility = View.VISIBLE // Ensure the RecyclerView is visible
//        }

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
                            it.id ?: "", tokenManager.getToken().toString()
                        )
                    )
                    wishlistAdapter.favouriteProductDeleted(it)
                }
            }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


}