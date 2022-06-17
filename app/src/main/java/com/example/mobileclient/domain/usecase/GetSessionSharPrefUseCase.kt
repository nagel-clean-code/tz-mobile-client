package com.example.mobileclient.domain.usecase

import com.example.mobileclient.domain.repository.UtcoinRepository

class GetSessionSharPrefUseCase(private val repository: UtcoinRepository) {
    fun execute() = repository.getSession()
}