package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileclient.Constants
import com.example.mobileclient.Constants.Companion.RESPONSE_SEARCH
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.databinding.FragmentSearchResultBinding
import com.example.mobileclient.presentation.adapters.SearchResultAdapter
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.contract.navigator
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.example.mobileclient.presentation.viewmodels.ShowSearchResultViewModel

class ShowSearchResultFragment : BaseFragment(), HasCustomActionToolbar {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModelShow: ShowSearchResultViewModel
    private lateinit var responseSearch: ResponseSearch
    private var searchAdapter: SearchResultAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
        binding = FragmentSearchResultBinding.inflate(layoutInflater)
        viewModelShow = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory(requireContext())
            )[ShowSearchResultViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        responseSearch = arguments?.get(RESPONSE_SEARCH) as ResponseSearch
        if (savedInstanceState == null && searchAdapter == null) {
            showResult()
        }
        setupListenerSelectedItem()
        return binding.root
    }

    private fun setupListenerSelectedItem() {
        viewModelShow.displayCampaign.observe(viewLifecycleOwner) {
            if (it != null) {
                displayCampaign(it)
                viewModelShow.displayCampaign.postValue(null)
            }
        }
        viewModelShow.displayProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                displayProduct(it)
                viewModelShow.displayProduct.postValue(null)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModelShow.displayCampaign.removeObservers(viewLifecycleOwner)
        viewModelShow.displayProduct.removeObservers(viewLifecycleOwner)
    }

    private fun displayCampaign(campaignsItem: CampaignsItem) =
        navigator().showInformationAboutCampaignFragment(campaignsItem)

    private fun displayProduct(productsItem: ProductsItem) =
        navigator().showInformationAboutProductFragment(productsItem)

    private fun showResult() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        searchAdapter =
            SearchResultAdapter(responseSearch.campaigns, responseSearch.products, viewModelShow, requireActivity().applicationContext)
        binding.recyclerView.adapter = searchAdapter
    }

    companion object {
        fun getNewInstance(responseSearch: ResponseSearch) = ShowSearchResultFragment().apply {
            arguments = Bundle().apply {
                putParcelable(RESPONSE_SEARCH, responseSearch)
            }
        }
    }

    override fun getCustomAction(): CustomAction = CustomAction(Constants.TYPE_ICON_GO_BACK)
}