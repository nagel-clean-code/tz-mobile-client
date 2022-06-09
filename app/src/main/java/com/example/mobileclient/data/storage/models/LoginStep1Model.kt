package com.example.mobileclient.data.storage.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class LoginStep1Model(
    @SerializedName("successful")
    var successful: Boolean = false,

    @SerializedName("errorMessage")
    var errorMessage: String? = "",

    @SerializedName("errorMessageCode")
    var errorMessageCode: String? = "",

    @SerializedName("settings")
    var settings: String? = null,        //TODO не известен тип, сделал пока String, нужна дока

    @SerializedName("normalizedPhone")
    var normalizedPhone: String? = ""
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    constructor(): this(false,"","",null,"")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (successful) 1 else 0)
        parcel.writeString(errorMessage)
        parcel.writeString(errorMessageCode)
        parcel.writeString(settings)
        parcel.writeString(normalizedPhone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginStep1Model> {
        override fun createFromParcel(parcel: Parcel): LoginStep1Model {
            return LoginStep1Model(parcel)
        }

        override fun newArray(size: Int): Array<LoginStep1Model?> {
            return arrayOfNulls(size)
        }
    }
}