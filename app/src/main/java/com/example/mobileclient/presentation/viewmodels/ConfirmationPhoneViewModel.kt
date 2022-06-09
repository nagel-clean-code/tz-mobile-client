package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.domain.usecase.ConfirmCodeUseCase
import com.example.mobileclient.domain.usecase.LoginPhoneUseCase

class ConfirmationPhoneViewModel(
    private val confirmCodeUseCase: ConfirmCodeUseCase,
    private val loginPhoneUseCase: LoginPhoneUseCase
) : BaseViewModel() {

    private val _loadResultMutableLiveData = MutableLiveResult<LoginStep2Model>()
    val loadResultLiveData: LiveResult<LoginStep2Model> = _loadResultMutableLiveData

    private val _retryResult = MutableLiveData<String>()
    val retryResult = _retryResult

    fun retry(number: String){
        try {
            loginPhoneUseCase.execute(number)
            _retryResult.value = "Код отправлен"
        }catch (e: Exception){
            _retryResult.value = "Не удалось повторить попытку"
        }
    }

    fun sending(number: String, code: String) = into(_loadResultMutableLiveData) {
        val result = confirmCodeUseCase.execute(number, code)
        if(result.errorMessage?.isBlank() == true){
            return@into result
        }else {
            throw Exception(result.errorMessage)
        }
    }
}