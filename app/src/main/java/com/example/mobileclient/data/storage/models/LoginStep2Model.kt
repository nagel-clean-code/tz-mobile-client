package com.example.mobileclient.data.storage.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LoginStep2Model(
    @SerializedName("successful")
    var successful: Boolean = true,

    @SerializedName("errorMessage")
    var errorMessage: String? = "",

    @SerializedName("errorMessageCode")
    var errorMessageCode: String? = "",

    @SerializedName("settings")
    var settings: Settings = Settings(2,2,"$"),

    @SerializedName("sessionId")
    var sessionId: String? = "6168171512"
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        TODO("settings"),
        parcel.readString()
    )

    constructor(): this(false, "", "", Settings(), "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (successful) 1 else 0)
        parcel.writeString(errorMessage)
        parcel.writeString(errorMessageCode)
        parcel.writeString(sessionId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginStep2Model> {
        override fun createFromParcel(parcel: Parcel): LoginStep2Model {
            return LoginStep2Model(parcel)
        }

        override fun newArray(size: Int): Array<LoginStep2Model?> {
            return arrayOfNulls(size)
        }
    }
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