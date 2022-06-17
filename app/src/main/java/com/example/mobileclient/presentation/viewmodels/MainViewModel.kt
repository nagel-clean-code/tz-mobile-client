package com.example.mobileclient.presentation.viewmodels

import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.domain.usecase.GetSessionSharPrefUseCase
import com.example.mobileclient.domain.usecase.SaveSessionSharPrefUseCase

class MainViewModel(
    private val saveSessionSharPrefUseCase: SaveSessionSharPrefUseCase,
    private val getSessionSharPrefUseCase: GetSessionSharPrefUseCase
): BaseViewModel() {

    fun saveSession(loginStep2Model: LoginStep2Model) = saveSessionSharPrefUseCase.execute(loginStep2Model)

    fun getSession(): LoginStep2Model? = getSessionSharPrefUseCase.execute()

}