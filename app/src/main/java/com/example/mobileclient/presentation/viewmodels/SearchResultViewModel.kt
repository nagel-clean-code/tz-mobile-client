package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.domain.usecase.SearchByStringUseCase
import com.example.mobileclient.presentation.adapters.SearchResultAdapter

class SearchResultViewModel(
    private val searchByStringUseCase: SearchByStringUseCase
): BaseViewModel(), SearchResultAdapter.ListenerItem {

    private val _loadResultMutableLiveData = MutableLiveResult<ResponseSearch>()
    val loadResultLiveData: LiveResult<ResponseSearch> = _loadResultMutableLiveData

    val displayProduct = MutableLiveData<ProductsItem>()
    val displayCampaign = MutableLiveData<CampaignsItem>()

    fun searchByString(str: String) = into(_loadResultMutableLiveData) {
        return@into searchByStringUseCase.execute(str)
    }

    override fun onDisplayProduct(product: ProductsItem) {
        displayProduct.value = product
    }

    override fun onDisplayCampaign(campaign: CampaignsItem) {
        displayCampaign.value = campaign
    }
}