package com.example.e_commerce.ui.home.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Category
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCategoriesBinding
import com.example.e_commerce.ui.features.subCategories.SubCategoriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    lateinit var viewModel: CategoriesViewModel
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
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
        viewModel.events.observe(viewLifecycleOwner, ::handleEvents)
        viewModel.handleAction(CategoriesContract.Action.LoadingCategories)
    }

    private fun initViews() {
        categoriesAdapter.onItemClickListener= CategoriesAdapter.OnItemClickListener{ _, item ->
            item?.let {
                viewModel.handleAction(CategoriesContract.Action.CategoryClicked(it))
            }


        }

        binding.categoriesRecycler.adapter = categoriesAdapter
    }

    private fun renderViewState(state: CategoriesContract.State) {
        when (state) {
            is CategoriesContract.State.Success -> bindCategories(state.category)
            is CategoriesContract.State.Loading -> showLoadind(state.message)
            is CategoriesContract.State.Error -> showError(state.message)

        }
    }

    private fun showError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text=message
        binding.btnTryAgain.setOnClickListener{
            viewModel.handleAction(CategoriesContract.Action.LoadingCategories)

        }
    }

    private fun showLoadind(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text=message
    }

    private fun bindCategories(category: List<Category?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
        binding.successView.isVisible = true
        categoriesAdapter.bindCategories(category)


    }

    private fun handleEvents(event: CategoriesContract.Event) {
        when (event) {
            is CategoriesContract.Event.NavigateToSubCategories -> navigateToSubCategory(event.category)

        }

    }

    private fun navigateToSubCategory(category: Category) {
        val subCategoriesFragment= SubCategoriesFragment()
        val bundle=Bundle()
        bundle.putParcelable("category",category)
        subCategoriesFragment.arguments=bundle
        Log.d("category","$category")
        childFragmentManager
            .beginTransaction()
            .replace(R.id.subcategories_fragment,subCategoriesFragment)
            .commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CategoriesFragment()
    }
}