package com.example.mobileclient.domain.repository

import com.example.mobileclient.data.storage.models.LoginStep2Model

interface UtcoinRepository {
    fun loginPhone(numberPhone: String): String
    fun confirmCode(numberPhone: String, code: String): LoginStep2Model
}