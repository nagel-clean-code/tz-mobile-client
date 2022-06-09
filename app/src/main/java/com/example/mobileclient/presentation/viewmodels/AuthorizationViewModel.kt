package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.domain.usecase.LoginPhoneUseCase

class AuthorizationViewModel(
    private val loginPhoneUseCase: LoginPhoneUseCase
): BaseViewModel() {

    private val _loadResultMutableLiveData = MutableLiveResult<LoginStep1Model>()
    val loadResultLiveData: LiveResult<LoginStep1Model> = _loadResultMutableLiveData


    fun sendCode(number: String) = into(_loadResultMutableLiveData) {
        val result = loginPhoneUseCase.execute(number)
        if(result.errorMessage?.isBlank() == true){
            return@into result
        }else {
            throw Exception(result.errorMessage)
        }
    }
}