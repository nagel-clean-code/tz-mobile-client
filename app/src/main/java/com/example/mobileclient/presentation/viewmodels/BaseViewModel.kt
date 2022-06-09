package com.example.mobileclient.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.presentation.models.state.ErrorResult
import com.example.mobileclient.presentation.models.state.PendingResult
import com.example.mobileclient.presentation.models.state.SuccessResult
import com.example.mobileclient.presentation.models.state.Result
import kotlinx.coroutines.launch

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>

open class BaseViewModel: ViewModel() {

    fun <T> into(liveResult: MutableLiveResult<T>, block: () -> T) = viewModelScope.launch {
        liveResult.value = PendingResult()
        try {
            liveResult.value = SuccessResult(block())
        } catch (e: Exception ) {
            liveResult.value = ErrorResult(e)
        }
    }
}