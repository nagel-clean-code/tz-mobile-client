package com.example.mobileclient.domain.usecase

import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.domain.repository.UtcoinRepository

class ConfirmCodeUseCase(private val repository: UtcoinRepository) {
    fun execute(numberPhone: String, code: String): LoginStep2Model =
        repository.confirmCode(numberPhone, code)
}