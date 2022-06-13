package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.domain.usecase.LoginPhoneUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationViewModel(
    private val loginPhoneUseCase: LoginPhoneUseCase
): BaseViewModel() {

    private val _loadResultMutableLiveData = MutableLiveResult<LoginStep1Model>()
    val loadResultLiveData: LiveResult<LoginStep1Model> = _loadResultMutableLiveData


    fun sendCode(number: String) = into(_loadResultMutableLiveData) {
        val result = withContext(Dispatchers.IO) {
            return@withContext loginPhoneUseCase.execute(number)
        }
        if(result.errorMessage == null || result.errorMessage!!.isBlank()){
            return@into result
        }else {
            throw Exception(result.errorMessage)
        }
    }
}