package com.example.mobileclient.data.storage.database

import android.content.ContentValues
import android.util.Log
import com.example.mobileclient.Constants
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class RestRequestImpl : RestRequest {
    private val okHttpClient = OkHttpClient()

    override fun loginPhoneStep1(numberPhone: String): LoginStep1Model {
        val result = request<LoginStep1Model>(Constants.LINK_LOGIN_STEP_2_API.format(numberPhone))
        result.first?.errorMessage = result.second
        return result.first!!
    }

    override fun confirmCode(numberPhone: String, code: String): LoginStep2Model {
        val result = request<LoginStep2Model>(Constants.LINK_LOGIN_STEP_2_API.format(numberPhone, code))
        result.first?.errorMessage = result.second
        return result.first!!
    }

    override fun search(str: String): ResponseSearch {
        val result = request<ResponseSearch>(Constants.LINK_SEARCH_API.format(str))
        return result.first!!
    }

    /** @return Pair<ModelData,ErrorMessage> */
    private fun <T> request(url: String): Pair<T?, String?> {
        var resultConfirm: T? = null
        var message: String? = null
        val countDownLatch = CountDownLatch(1)
        GET(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.w(ContentValues.TAG, e)
                message = "Ошибка запроса"
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                val respString: String? = response.body?.string()
                Log.d(ContentValues.TAG, "Запрос выполнен успешно: $respString")
                try {
                    val type = object : TypeToken<T>() {}.type
                    resultConfirm = Gson().fromJson(respString, type)!!
                } catch (e: Exception) {
                    Log.w(ContentValues.TAG, e)
                    message = "Неудалось распарсить ответ"
                }
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return Pair(resultConfirm, message)
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