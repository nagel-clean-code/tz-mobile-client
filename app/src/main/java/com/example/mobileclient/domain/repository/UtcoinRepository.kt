package com.example.mobileclient.domain.repository

interface UtcoinRepository {
    fun loginPhone(numberPhone: String): String
}