package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.data.repository.UtcoinRepositoryImpl
import com.example.mobileclient.data.storage.database.RestRequestImpl
import com.example.mobileclient.domain.usecase.ConfirmCodeUseCase
import com.example.mobileclient.domain.usecase.LoginPhoneUseCase


class ModelFactory() : ViewModelProvider.NewInstanceFactory() {

    private val utcoinRepository by lazy(LazyThreadSafetyMode.NONE) {
        UtcoinRepositoryImpl(RestRequestImpl())
    }

    private val loginPhoneUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoginPhoneUseCase(utcoinRepository)
    }

    private val confirmCodeUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ConfirmCodeUseCase(utcoinRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ConfirmationPhoneViewModel::class.java -> ConfirmationPhoneViewModel(
                confirmCodeUseCase,
                loginPhoneUseCase
            ) as T
            AuthorizationViewModel::class.java -> AuthorizationViewModel(loginPhoneUseCase) as T
            else -> {
                throw IllegalArgumentException("Не верный тип ViewModel")
            }
        }
    }
}