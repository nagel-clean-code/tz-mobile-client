package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.domain.usecase.SearchByStringUseCase

class SearchResultViewModel(
    private val searchByStringUseCase: SearchByStringUseCase
): BaseViewModel() {

    private val _loadResultMutableLiveData = MutableLiveResult<ResponseSearch>()
    val loadResultLiveData: LiveResult<ResponseSearch> = _loadResultMutableLiveData

    fun searchByString(str: String) = into(_loadResultMutableLiveData) {
        return@into searchByStringUseCase.execute(str)
    }
}