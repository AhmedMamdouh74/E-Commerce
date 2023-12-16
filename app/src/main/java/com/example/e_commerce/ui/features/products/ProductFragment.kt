package com.example.e_commerce.ui.features.products

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
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProductBinding
import com.example.e_commerce.ui.features.products.details.ProductDetailsFragment
import com.example.e_commerce.ui.features.subCategories.SubCategoriesAdapter
import com.example.e_commerce.ui.features.subCategories.SubCategoriesContract
import com.example.e_commerce.ui.features.subCategories.SubCategoriesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {
    lateinit var category: Category
    private lateinit var viewModel: ProductsViewModel
    lateinit var product: Product


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        //getProduct(product)

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
                Log.d( "initViews: ","$product")
            }

        binding.productsRecycler.adapter = productsAdapter
    }

    private fun handleEvents(event: ProductContract.Event?) {
        when (event) {
            is ProductContract.Event.NavigateToProductsDetails -> navigateToProductsDetails(event.product)

            else -> {}
        }

    }

    private fun navigateToProductsDetails(product: Product) {
//       val productDetailsFragment= ProductDetailsFragment()
//      val bundle=Bundle()
//   bundle.putParcelable("category",product)
//       productDetailsFragment.arguments=bundle
//        Log.d("productFragment","$product")
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,ProductDetailsFragment.getInstance(product))
            .commit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = arguments?.getParcelable("product") ?: Product() // Provide default value if necessary


        initViews()
        viewModel.state.observe(viewLifecycleOwner, ::renderViewStates)
        viewModel.event.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(ProductContract.Action.LoadingProducts(category._id ?: ""))


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
            ProductContract.Action.LoadingProducts(category._id ?: "")
        }
    }

    companion object {
        fun getInstance(category: Category): ProductFragment {
            val productFragmentRef = ProductFragment()
            productFragmentRef.category = category
            return productFragmentRef
        }
        fun getProduct(product: Product):ProductFragment{
            val productFragmentRef=ProductFragment()
          //  productFragmentRef.product=product
            productFragmentRef.arguments = Bundle().apply {
                putParcelable("product", product)
            }
            return productFragmentRef
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}