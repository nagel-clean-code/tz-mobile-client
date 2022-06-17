package com.example.mobileclient.domain.usecase

import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.domain.repository.UtcoinRepository

class SaveSessionSharPrefUseCase(private val repository: UtcoinRepository) {
    fun execute(loginStep2Model: LoginStep2Model) = repository.saveSession(loginStep2Model)
}