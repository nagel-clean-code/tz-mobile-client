package com.example.mobileclient.domain.usecase

import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.domain.repository.UtcoinRepository

class SearchByStringUseCase(private val repository: UtcoinRepository) {
    suspend fun execute(str: String): ResponseSearch = repository.search(str)
}