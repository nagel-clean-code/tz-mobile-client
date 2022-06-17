package com.example.mobileclient.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.data.repository.UtcoinRepositoryImpl
import com.example.mobileclient.data.storage.database.RestRequestImpl
import com.example.mobileclient.data.storage.sharedprefs.SharedPrefMainImpl
import com.example.mobileclient.domain.usecase.*


class ModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {

    private val utcoinRepository by lazy(LazyThreadSafetyMode.NONE) {
        UtcoinRepositoryImpl(RestRequestImpl(), SharedPrefMainImpl(context))
    }

    private val loginPhoneUseCase by lazy(LazyThreadSafetyMode.NONE) {
        LoginPhoneUseCase(utcoinRepository)
    }

    private val confirmCodeUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ConfirmCodeUseCase(utcoinRepository)
    }

    private val searchByStringUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SearchByStringUseCase(utcoinRepository)
    }

    private val saveSessionSharPrefUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SaveSessionSharPrefUseCase(utcoinRepository)
    }

    private val getSessionSharPrefUseCase by lazy(LazyThreadSafetyMode.NONE) {
        GetSessionSharPrefUseCase(utcoinRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ConfirmationPhoneViewModel::class.java -> ConfirmationPhoneViewModel(
                confirmCodeUseCase,
                loginPhoneUseCase
            ) as T
            AuthorizationViewModel::class.java -> AuthorizationViewModel(loginPhoneUseCase) as T
            ShowSearchResultViewModel::class.java -> ShowSearchResultViewModel() as T
            InformationAboutElementViewModel::class.java -> InformationAboutElementViewModel() as T
            SearchViewModel::class.java -> SearchViewModel(searchByStringUseCase) as T
            MainViewModel::class.java -> MainViewModel(
                saveSessionSharPrefUseCase,
                getSessionSharPrefUseCase
            ) as T
            else -> {
                throw IllegalArgumentException("Не верный тип ViewModel")
            }
        }
    }
}