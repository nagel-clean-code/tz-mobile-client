package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.data.repository.UtcoinRepositoryImpl
import com.example.mobileclient.data.storage.database.RestRequestImpl
import com.example.mobileclient.domain.usecase.LoginPhoneUseCase


class ModelFactory() : ViewModelProvider.NewInstanceFactory() {

    private val utcoinRepository by lazy(LazyThreadSafetyMode.NONE){
        UtcoinRepositoryImpl(RestRequestImpl())
    }

    private val loginPhoneUseCase by lazy(LazyThreadSafetyMode.NONE){
        LoginPhoneUseCase(utcoinRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass::class.java) {
            ConfirmationPhoneViewModel::class.java -> ConfirmationPhoneViewModel() as T
            AuthorizationViewModel::class.java -> AuthorizationViewModel(loginPhoneUseCase) as T
            else -> {
                return AuthorizationViewModel(loginPhoneUseCase) as T
//                throw IllegalArgumentException("Не верный тип ViewModel")
            }
        }
    }
}