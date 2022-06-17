package com.example.mobileclient.data.repository

import com.example.mobileclient.data.storage.database.RestRequest
import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.data.storage.sharedprefs.SharedPrefMain
import com.example.mobileclient.domain.repository.UtcoinRepository

class UtcoinRepositoryImpl(
    private val restRequest: RestRequest,
    private val sharPref: SharedPrefMain
    ): UtcoinRepository {

    override suspend fun loginPhone(numberPhone: String): LoginStep1Model = restRequest.loginPhoneStep1(numberPhone)
    override suspend fun confirmCode(numberPhone: String, code: String) = restRequest.confirmCode(numberPhone, code)
    override suspend fun search(str: String): ResponseSearch = restRequest.search(str)
    override fun saveSession(loginStep2Model: LoginStep2Model) = sharPref.saveSession(loginStep2Model)
    override fun getSession(): LoginStep2Model? = sharPref.getSession()
}