package com.example.mobileclient.data.storage.database

import android.content.ContentValues
import android.util.Log
import com.example.mobileclient.Constants
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch


class RestRequestImpl : RestRequest {
    private val okHttpClient = OkHttpClient()

    override fun loginPhoneStap1(numberPhone: String): String {
        var resultNumber: String = ""
        val url = Constants.LINK_LOGIN_STEP_1_API.format(numberPhone)
        val countDownLatch = CountDownLatch(1)
        GET(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.w(ContentValues.TAG, e)
                countDownLatch.countDown()
                throw Exception("Ошибка запроса")
            }

            override fun onResponse(call: Call, response: Response) {
                val respString: String? = response.body?.string()
                Log.d(ContentValues.TAG, "Запрос выполнен успешно: $respString")

                val result: LoginStep1Model?
                try {
                    val type = object : TypeToken<LoginStep1Model>() {}.type
                    result = Gson().fromJson(respString, type)!!
                } catch (e: Exception) {
                    Log.w(ContentValues.TAG, e)
                    throw IllegalStateException("Неудалось распарсить ответ")
                }
                if (result.successful) {
                    resultNumber = result.normalizedPhone
                    countDownLatch.countDown()
                } else {
                    throw IllegalArgumentException("Ошибочный ответ на запрос")
                }
            }
        })
        countDownLatch.await()
        return resultNumber
    }

    override fun confirmCode(numberPhone: String, code: String): LoginStep2Model {
        var resultConfirm: LoginStep2Model? = null
        val url = Constants.LINK_LOGIN_STEP_2_API.format(numberPhone,code)
        val countDownLatch = CountDownLatch(1)
        GET(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.w(ContentValues.TAG, e)
                countDownLatch.countDown()
                throw Exception("Ошибка запроса")
            }

            override fun onResponse(call: Call, response: Response) {
                val respString: String? = response.body?.string()
                Log.d(ContentValues.TAG, "Запрос выполнен успешно: $respString")
                try {
                    val type = object : TypeToken<LoginStep2Model>() {}.type
                    resultConfirm = Gson().fromJson(respString, type)!!
                } catch (e: Exception) {
                    Log.w(ContentValues.TAG, e)
                    throw IllegalStateException("Неудалось распарсить ответ")
                }
                if (resultConfirm!!.successful) {
                    countDownLatch.countDown()
                } else {
                    throw IllegalArgumentException("Ошибочный ответ на запрос")
                }
            }
        })
        countDownLatch.await()
        return resultConfirm!!
    }

    fun GET(url: String, callback: Callback): Call {
        val request = Request.Builder()
            .url(url)
            .build()

        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }


}