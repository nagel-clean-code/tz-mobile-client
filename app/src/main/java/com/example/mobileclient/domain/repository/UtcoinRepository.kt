package com.example.mobileclient.domain.repository

import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.ResponseSearch

interface UtcoinRepository {
    suspend fun loginPhone(numberPhone: String): LoginStep1Model
    suspend fun confirmCode(numberPhone: String, code: String): LoginStep2Model
    suspend fun search(str: String): ResponseSearch
}