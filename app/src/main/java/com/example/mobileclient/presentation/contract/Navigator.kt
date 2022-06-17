package com.example.mobileclient.presentation.contract

import androidx.fragment.app.Fragment
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.data.storage.models.ResponseSearch

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showFragmentSearchResult(responseSearch: ResponseSearch)
    fun showFragmentSearch(loginStep2Model: LoginStep2Model)
    fun showFragmentConfirmationPhone(numberPhone: String)
    fun showFragmentLogin()
    fun showInformationAboutCampaignFragment(campaignsItem: CampaignsItem)
    fun showInformationAboutProductFragment(productsItem: ProductsItem)

    fun goBack()

}