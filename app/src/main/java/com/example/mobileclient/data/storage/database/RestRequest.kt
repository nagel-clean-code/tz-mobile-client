package com.example.mobileclient.data.storage.database

import com.example.mobileclient.data.storage.models.LoginStep2Model


interface RestRequest {
    fun loginPhoneStap1(numberPhone: String): String
    fun confirmCode(numberPhone: String, code: String): LoginStep2Model
}