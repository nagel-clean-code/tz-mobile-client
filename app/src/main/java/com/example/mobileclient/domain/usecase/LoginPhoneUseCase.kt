package com.example.mobileclient.domain.usecase

import com.example.mobileclient.domain.repository.UtcoinRepository

/**
 * Отправка сообщения с кодом на номер телефона
 * @return номер телефона в правильном формате
 */
class LoginPhoneUseCase(private val repository: UtcoinRepository) {
    fun execute(numberPhone: String): String = repository.loginPhone(numberPhone)

}