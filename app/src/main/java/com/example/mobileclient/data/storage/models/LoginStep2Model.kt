package com.example.mobileclient.data.storage.models

import com.google.gson.annotations.SerializedName

data class LoginStep2Model(
    @SerializedName("successful")
    var successful: Boolean = true,

    @SerializedName("errorMessage")
    var errorMessage: String = "",

    @SerializedName("errorMessageCode")
    var errorMessageCode: String = "",

    @SerializedName("settings")
    var settings: Settings = Settings(2,2,"$"),

    @SerializedName("sessionId")
    var sessionId: String = "6168171512"
) {
    constructor(): this(false, "", "", Settings(), "")
}

data class Settings(
    @SerializedName("moneyFraction")
    var moneyFraction: Int = 2,

    @SerializedName("tokenFraction")
    var tokenFraction: Int = 2,

    @SerializedName("mainCurrencyDisplay")
    var mainCurrencyDisplay: String = "$"
){
    constructor(): this(2,2,"$")
}