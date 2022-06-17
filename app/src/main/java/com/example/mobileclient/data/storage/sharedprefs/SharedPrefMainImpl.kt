package com.example.mobileclient.data.storage.sharedprefs

import android.content.Context
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.google.gson.Gson


class SharedPrefMainImpl(context: Context) : SharedPrefMain {
    private val gson = Gson()

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_MAIN, Context.MODE_PRIVATE)

    override fun saveSession(loginStep2Model: LoginStep2Model) {
        val json = gson.toJson(loginStep2Model)
        sharedPreferences.edit().putString(SESSION, json).apply()
    }

    override fun getSession(): LoginStep2Model? {
        val json = sharedPreferences.getString(SESSION, "") ?: ""
        if(json.isBlank()){
            return null
        }
        return gson.fromJson(json, LoginStep2Model::class.java)
    }

    private companion object {
        const val SHARED_PREFS_MAIN = "SHARED_PREFS_MAIN"
        const val SESSION = "SESSION"
    }
}