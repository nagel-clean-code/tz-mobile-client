package com.example.mobileclient.data.storage.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseSearch(

	@field:SerializedName("settings")
	val settings: Any? = null,

	@field:SerializedName("errorMessageCode")
	val errorMessageCode: String? = null,

	@field:SerializedName("campaigns")
	val campaigns: List<CampaignsItem?>? = null,

	@field:SerializedName("moreCampaigns")
	val moreCampaigns: Boolean? = null,

	@field:SerializedName("more")
	val more: Boolean? = null,

	@field:SerializedName("errorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("successful")
	val successful: Boolean? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		TODO("settings"),
		parcel.readString(),
		parcel.createTypedArrayList(CampaignsItem.CREATOR),
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readString(),
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.createTypedArrayList(ProductsItem.CREATOR)
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(errorMessageCode)
		parcel.writeTypedList(campaigns)
		parcel.writeValue(moreCampaigns)
		parcel.writeValue(more)
		parcel.writeString(errorMessage)
		parcel.writeValue(successful)
		parcel.writeTypedList(products)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseSearch> {
		override fun createFromParcel(parcel: Parcel): ResponseSearch {
			return ResponseSearch(parcel)
		}

		override fun newArray(size: Int): Array<ResponseSearch?> {
			return arrayOfNulls(size)
		}
	}
}

data class ActionsItem(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("value")
	val value: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(text)
		parcel.writeString(value)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ActionsItem> {
		override fun createFromParcel(parcel: Parcel): ActionsItem {
			return ActionsItem(parcel)
		}

		override fun newArray(size: Int): Array<ActionsItem?> {
			return arrayOfNulls(size)
		}
	}
}

data class CampaignsItem(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("paymentTime")
	val paymentTime: String? = null,

	@field:SerializedName("actions")
	val actions: List<ActionsItem?>? = null,

	@field:SerializedName("cashback")
	val cashback: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.createTypedArrayList(ActionsItem),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(imageUrl)
		parcel.writeString(name)
		parcel.writeValue(id)
		parcel.writeString(paymentTime)
		parcel.writeTypedList(actions)
		parcel.writeString(cashback)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CampaignsItem> {
		override fun createFromParcel(parcel: Parcel): CampaignsItem {
			return CampaignsItem(parcel)
		}

		override fun newArray(size: Int): Array<CampaignsItem?> {
			return arrayOfNulls(size)
		}
	}
}

data class ProductsItem(

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("campaignImageUrl")
	val campaignImageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("paymentTime")
	val paymentTime: String? = null,

	@field:SerializedName("actions")
	val actions: List<ActionsItem?>? = null,

	@field:SerializedName("campaignName")
	val campaignName: String? = null,

	@field:SerializedName("cashback")
	val cashback: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.createStringArrayList(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.createTypedArrayList(ActionsItem),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(price)
		parcel.writeStringList(imageUrls)
		parcel.writeString(name)
		parcel.writeString(campaignImageUrl)
		parcel.writeValue(id)
		parcel.writeString(paymentTime)
		parcel.writeTypedList(actions)
		parcel.writeString(campaignName)
		parcel.writeString(cashback)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ProductsItem> {
		override fun createFromParcel(parcel: Parcel): ProductsItem {
			return ProductsItem(parcel)
		}

		override fun newArray(size: Int): Array<ProductsItem?> {
			return arrayOfNulls(size)
		}
	}
}
