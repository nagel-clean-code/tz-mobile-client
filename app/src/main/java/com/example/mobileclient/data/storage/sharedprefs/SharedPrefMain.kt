package com.example.mobileclient.data.storage.sharedprefs

import com.example.mobileclient.data.storage.models.LoginStep2Model

interface SharedPrefMain {
    fun saveSession(loginStep2Model: LoginStep2Model)
    fun getSession(): LoginStep2Model?
}