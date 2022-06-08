package com.example.mobileclient.data.repository

import com.example.mobileclient.data.storage.database.RestRequest
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.domain.repository.UtcoinRepository

class UtcoinRepositoryImpl(private val restRequest: RestRequest): UtcoinRepository {

    override fun loginPhone(numberPhone: String): String = restRequest.loginPhoneStap1(numberPhone)
    override fun confirmCode(numberPhone: String, code: String) = restRequest.confirmCode(numberPhone, code)

}