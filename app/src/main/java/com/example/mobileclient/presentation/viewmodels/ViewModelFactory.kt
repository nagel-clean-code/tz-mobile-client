package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.data.repository.UtcoinRepositoryImpl


class ModelFactory() : ViewModelProvider.NewInstanceFactory() {

    private val utcoinRepository by lazy(LazyThreadSafetyMode.NONE){
        UtcoinRepositoryImpl()
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass::class.java) {
            ConfirmationPhoneViewModel::class.java -> ConfirmationPhoneViewModel() as T
            AuthorizationViewModel::class.java -> AuthorizationViewModel() as T
            else -> {
                throw IllegalArgumentException("Не верный тип ViewModel")
            }
        }
    }
}