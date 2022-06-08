package com.example.mobileclient.data.storage.models

import com.google.gson.annotations.SerializedName


data class LoginStep1Model(
    @SerializedName("successful")
    var successful: Boolean = false,

    @SerializedName("errorMessage")
    var errorMessage: String = "",

    @SerializedName("errorMessageCode")
    var errorMessageCode: String = "",

    @SerializedName("settings")
    var settings: String? = null,        //TODO не известен тип, сделал пока String, нужна дока

    @SerializedName("normalizedPhone")
    var normalizedPhone: String = ""
){
    constructor(): this(false,"","",null,"")
}