package com.example.e_commerce.ui.features.subCategories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Category
import com.example.domain.model.SubCategories
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSubCategoriesBinding
import com.example.e_commerce.ui.features.products.ProductFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoriesFragment : Fragment() {
    lateinit var viewModel: SubCategoriesViewModel

    //    companion object {
//        fun getInstance(category: Category): SubCategoriesFragment {
//            val subCategoriesFragmentRef = SubCategoriesFragment()
//            subCategoriesFragmentRef.category = category
//            return subCategoriesFragmentRef
//        }
//    }
    lateinit var category: Category

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
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.states.observe(viewLifecycleOwner, ::renderViewState)
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
                            category._id?: ""
                        )
                    )
                }
            }

        binding.subcategoriesRecycler.adapter = subCategoriesAdapter
    }

    private fun renderViewState(state: SubCategoriesContract.State) {
        when (state) {
            is SubCategoriesContract.State.Success -> bindCategories(state.subcategory)
            is SubCategoriesContract.State.Loading -> showLoadind(state.message)
            is SubCategoriesContract.State.Error -> showError(state.message)

            else -> {}
        }
    }

    private fun showError(message: String) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.errorText.text = message
        binding.btnTryAgain.setOnClickListener {
            viewModel.handleAction(
                SubCategoriesContract.Action.LoadingSubCategories(
                    category._id ?: ""
                )
            )
        }
    }

    private fun showLoadind(message: String) {
        binding.loadingView.isVisible = true
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
        binding.loadingText.text = message
    }

    private fun bindCategories(category: List<SubCategories?>) {
        binding.loadingView.isVisible = false
        binding.errorView.isVisible = false
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
        val productFragment= ProductFragment()
        val bundle=Bundle()
        bundle.putParcelable("category",category)
        productFragment.arguments=bundle
        Log.d("category","$category")
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
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