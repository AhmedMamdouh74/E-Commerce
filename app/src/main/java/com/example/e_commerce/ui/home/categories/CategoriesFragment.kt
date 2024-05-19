package com.example.e_commerce.ui.home.categories

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
import com.example.domain.model.Category
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCategoriesBinding
import com.example.e_commerce.ui.common.customviews.ProgressDialog
import com.example.e_commerce.ui.features.cart.CartActivity
import com.example.e_commerce.ui.features.subcategories.SubCategoriesFragment
import com.example.e_commerce.ui.home.showRetrySnakeBarError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var viewModel: CategoriesViewModel
    private val progressDialog by lazy { ProgressDialog.createProgressDialog(requireContext()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
    }

    private var _viewBinding: FragmentCategoriesBinding? = null
    private val binding get() = _viewBinding!!
    private val categoriesAdapter = CategoriesAdapter(null)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)

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
        viewModel.handleAction(CategoriesContract.Action.LoadingCategories)
    }

    private fun initViews() {
        categoriesAdapter.onItemClickListener = CategoriesAdapter.OnItemClickListener { _, item ->
            item?.let {
                viewModel.handleAction(CategoriesContract.Action.CategoryClicked(it))
            }

        }
        binding.categoriesRecycler.adapter = categoriesAdapter
        binding.icCart.setOnClickListener {
            viewModel.handleAction(CategoriesContract.Action.CartClicked)
        }
    }

    private fun renderViewState(state: CategoriesContract.State) {
        when (state) {
            is CategoriesContract.State.Success -> bindCategories(state.category)
            is CategoriesContract.State.Loading -> showLoading()
            is CategoriesContract.State.Error -> showError(state.message)

            else -> {}
        }
    }

    private fun showError(message: String) {
        progressDialog.dismiss()
        binding.successView.isVisible = true
        view?.showRetrySnakeBarError(message) {
            viewModel.handleAction(CategoriesContract.Action.LoadingCategories)

        }
    }

    private fun showLoading() {
        progressDialog.show()
        binding.successView.isVisible = true

    }

    private fun bindCategories(category: List<Category?>) {
        progressDialog.dismiss()
        binding.successView.isVisible = true
        categoriesAdapter.bindCategories(category)


    }

    private fun handleEvents(event: CategoriesContract.Event) {
        when (event) {
            is CategoriesContract.Event.NavigateToSubCategories -> navigateToSubCategory(event.category)

            CategoriesContract.Event.NavigateToCart -> navigateToCart()
            else -> {}
        }

    }

    private fun navigateToCart() {
        startActivity(Intent(requireActivity(), CartActivity::class.java))
    }

    private fun navigateToSubCategory(category: Category) {
        val subCategoriesFragment = SubCategoriesFragment()
        val bundle = Bundle()
        bundle.putParcelable("category", category)
        subCategoriesFragment.arguments = bundle
        childFragmentManager
            .beginTransaction()
            .addToBackStack("category")
            .replace(R.id.subcategories_fragment, subCategoriesFragment)
            .commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}