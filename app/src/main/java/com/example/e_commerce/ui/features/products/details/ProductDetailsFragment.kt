package com.example.e_commerce.ui.features.products.details

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var viewModel: ProductDetailsViewModel
    lateinit var product: Product


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        getProduct()


    }

    private var viewBinding: FragmentProductDetailsBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::renderViewStates)
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(ProductsDetailsContract.Action.LoadingProduct(product.id ?: ""))
    }

    private fun handleEvents(event: ProductsDetailsContract.Event?) {
        when (event) {
            ProductsDetailsContract.Event.NavigateToCart -> TODO()

            else -> {}
        }

    }


    private fun renderViewStates(state: ProductsDetailsContract.State?) {
        when (state) {
            is ProductsDetailsContract.State.Error -> showError(state.message)
            is ProductsDetailsContract.State.Loading -> showLoading(state.message)
            is ProductsDetailsContract.State.Success -> bindProductDetail()

            else -> {}
        }

    }

    private fun bindProductDetail() {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.loadingView.isVisible = false
    }

    private fun showLoading(message: String) {
        binding.loadingView.isVisible = true
        binding.successView.isVisible = false
        binding.errorView.isVisible = false
        binding.errorText.text = message

    }

    private fun showError(message: String) {
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.loadingView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            ProductsDetailsContract.Action.LoadingProduct(product.id ?: "")
        }
    }



    companion object {

        fun getInstance(product: Product): ProductDetailsFragment {
            var productDetailsFragmentRef = ProductDetailsFragment()
            productDetailsFragmentRef.product = product
            return productDetailsFragmentRef


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
    private fun getProduct() {
        val bundle: Bundle? = this.arguments
        if (bundle != null) {
            product = bundle.getParcelable("category")!!
            Log.d("productDetails", "$product")
        }

    }
}