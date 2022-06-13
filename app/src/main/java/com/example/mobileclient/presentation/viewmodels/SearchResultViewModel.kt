package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.presentation.adapters.SearchResultAdapter

class SearchResultViewModel(
): BaseViewModel(), SearchResultAdapter.ListenerItem {

    val displayProduct = MutableLiveData<ProductsItem>()
    val displayCampaign = MutableLiveData<CampaignsItem>()

    override fun onDisplayProduct(product: ProductsItem) {
        displayProduct.value = product
    }

    override fun onDisplayCampaign(campaign: CampaignsItem) {
        displayCampaign.value = campaign
    }
}