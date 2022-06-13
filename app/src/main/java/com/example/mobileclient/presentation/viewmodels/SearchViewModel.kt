package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.domain.usecase.SearchByStringUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val searchByStringUseCase: SearchByStringUseCase
): BaseViewModel(){

    private val _loadResultMutableLiveData = MutableLiveResult<ResponseSearch>()
    val loadResultLiveData: LiveResult<ResponseSearch> = _loadResultMutableLiveData

    fun searchByString(str: String) = into(_loadResultMutableLiveData){
        val result = withContext(Dispatchers.IO) {
            return@withContext searchByStringUseCase.execute(str)
        }
        return@into result
    }
}