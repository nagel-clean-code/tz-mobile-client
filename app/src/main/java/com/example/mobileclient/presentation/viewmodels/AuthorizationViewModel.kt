package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.domain.usecase.LoginPhoneUseCase
import com.example.mobileclient.presentation.models.state.SuccessResult

class AuthorizationViewModel(
    private val loginPhoneUseCase: LoginPhoneUseCase
): BaseViewModel() {

    private val _loadResultMutableLiveData = MutableLiveResult<String>(SuccessResult(String()))
    val loadResultLiveData: LiveResult<String> = _loadResultMutableLiveData


    fun sendCode(number: String) = into(_loadResultMutableLiveData) {
        loginPhoneUseCase.execute(number)
    }
}