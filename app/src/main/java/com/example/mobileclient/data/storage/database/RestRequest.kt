package com.example.mobileclient.data.storage.database

import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.data.storage.models.LoginStep2Model


interface RestRequest {
    fun loginPhoneStep1(numberPhone: String): LoginStep1Model
    fun confirmCode(numberPhone: String, code: String): LoginStep2Model
}