package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("test") var test: Boolean? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("locale") var locale: String? = null,
    @SerializedName("images") var images: ArrayList<ImageResponse> = arrayListOf(),
    @SerializedName("postalCode") var postalCode: String? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("city") var city: CityResponse? = CityResponse(),
    @SerializedName("state") var state: StateResponse? = StateResponse(),
    @SerializedName("country") var country: CountryResponse? = CountryResponse()
)