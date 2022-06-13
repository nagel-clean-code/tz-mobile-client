package com.example.mobileclient.presentation.contract

/** Имплементируйте интерфейс, если ваш фрагмент переопределяет иконку в тулбаре */

interface  HasCustomActionToolbar {
    fun getCustomAction(): CustomAction
}

class CustomAction(
    val typeIcon: Int   //
)