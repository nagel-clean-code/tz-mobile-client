package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileclient.Constants.Companion.LOGIN_STEP_2_MODEL
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.databinding.FragmentSearchResultBinding
import com.example.mobileclient.presentation.adapters.SearchResultAdapter
import com.example.mobileclient.presentation.models.state.takeSuccess
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.example.mobileclient.presentation.viewmodels.SearchResultViewModel

class SearchResultFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var resultAuthorization: LoginStep2Model
    private lateinit var searchAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchResultBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[SearchResultViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultAuthorization = arguments?.get(LOGIN_STEP_2_MODEL) as LoginStep2Model
        textEditListenerFormSearch()
        setupListenerResult()
        setupListenerSelectedItem()
        return binding.root
    }

    private fun setupListenerSelectedItem() {
        viewModel.displayCampaign.observe(viewLifecycleOwner) {
            displayCampaign(it)
        }
        viewModel.displayProduct.observe(viewLifecycleOwner) {
            displayProduct(it)
        }
    }

    private fun displayCampaign(campaignsItem: CampaignsItem) {
        binding.mainConstraint.visibility = View.GONE
        childFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container_fragment,
                InformationAboutElementFragment.getNewInstance(campaignsItem)
            ).commit()
    }

    private fun displayProduct(productsItem: ProductsItem) {
        binding.mainConstraint.visibility = View.GONE
        childFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container_fragment,
                InformationAboutElementFragment.getNewInstance(productsItem)
            ).commit()
    }

    private fun setupListenerResult() {
        viewModel.loadResultLiveData.observe(viewLifecycleOwner) { result ->
            renderResult(
                root = binding.root,
                result = result,
                onSuccessResult = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    showResult()
                },
                onPending = {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textError.visibility = View.GONE
                },
                onError = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.text = it.localizedMessage
                    binding.textError.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun showResult() {
        binding.searchTextInputLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        val result = viewModel.loadResultLiveData.value?.takeSuccess() as ResponseSearch
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        searchAdapter = SearchResultAdapter(result.campaigns, result.products, viewModel)
        binding.recyclerView.adapter = searchAdapter
    }

    private fun textEditListenerFormSearch() {
        binding.searchEditText.setOnEditorActionListener { _, _, keyEvent ->
            if (keyEvent == null || (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                viewModel.searchByString(binding.searchEditText.text.toString())
                return@setOnEditorActionListener false
            } else {
                return@setOnEditorActionListener true
            }
        }
    }

    companion object {
        fun getNewInstance(modelResult: LoginStep2Model) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LOGIN_STEP_2_MODEL, modelResult)
            }
        }
    }
}