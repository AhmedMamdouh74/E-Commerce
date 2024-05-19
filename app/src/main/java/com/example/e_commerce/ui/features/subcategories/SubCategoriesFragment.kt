package com.example.e_commerce.ui.features.subcategories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSubCategoriesBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.products.ProductFragment
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubCategoriesFragment : Fragment() {
    private lateinit var viewModel: SubCategoriesViewModel
    lateinit var category: Category
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SubCategoriesViewModel::class.java]
        getCategory()


    }

    private var _viewBinding: FragmentSubCategoriesBinding? = null
    private val binding get() = _viewBinding!!
    private val subCategoriesAdapter = SubCategoriesAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSubCategoriesBinding.inflate(inflater, container, false)
        Log.d("TAG", "navigateToCategoriesProductsAhmed: ")
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { renderViewState(it) }

            }
        }

        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(
            SubCategoriesContract.Action.LoadingSubCategories(
                category._id ?: ""
            )
        )
    }

    private fun initViews() {
        subCategoriesAdapter.onItemClickListener =
            SubCategoriesAdapter.OnItemClickListener { position, item ->
                item?.let {
                    viewModel.handleAction(
                        SubCategoriesContract.Action.SubCategoriesClicked(
                            category._id ?: ""
                        )
                    )
                }
            }

        binding.subcategoriesRecycler.adapter = subCategoriesAdapter
    }

    private fun renderViewState(state: SubCategoriesContract.State) {
        when (state) {
            is SubCategoriesContract.State.Success -> bindCategories(state.subcategory)
            is SubCategoriesContract.State.Loading -> showLoading()
            is SubCategoriesContract.State.Error -> showError(state.message)

            else -> {}
        }
    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        binding.successView.isVisible = false

        view?.showRetrySnakeBarError(message) {
            viewModel.handleAction(
                SubCategoriesContract.Action.LoadingSubCategories(
                    category._id ?: ""
                )
            )
        }
    }

    private fun showLoading() {
        progressDialog.show()
        binding.successView.isVisible = false

    }

    private fun bindCategories(category: List<SubCategories?>) {
        progressDialog.dismiss()
        binding.successView.isVisible = true
        subCategoriesAdapter.bindCategories(category)

    }

    private fun handleEvents(event: SubCategoriesContract.Event) {
        when (event) {
            is SubCategoriesContract.Event.NavigateToCategoriesProducts -> navigateToCategoriesProducts(
                event.categoryId
            )

            else -> {}
        }

    }

    private fun navigateToCategoriesProducts(categoryId: String) {
        val productFragment = ProductFragment()
        val bundle = Bundle()
        bundle.putParcelable("category", category)
        productFragment.arguments = bundle
        Log.d("category", "$category")
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack("subCategory")
            .replace(R.id.fragment_container, ProductFragment.getInstance(category))
            .commit()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun getCategory() {
        val bundle: Bundle? = this.arguments
        if (bundle != null) {
            category = bundle.getParcelable("category")!!
            Log.d("subcategory", "$category")
        }

    }


}