package com.example.mobileclient.domain.usecase

import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.domain.repository.UtcoinRepository

/**
 * Отправка сообщения с кодом на номер телефона
 * @return номер телефона в правильном формате
 */
class LoginPhoneUseCase(private val repository: UtcoinRepository) {
    fun execute(numberPhone: String): LoginStep1Model = repository.loginPhone(numberPhone)

}