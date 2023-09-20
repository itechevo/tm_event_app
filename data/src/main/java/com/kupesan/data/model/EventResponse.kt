package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("name") var name: String,
    @SerializedName("type") var type: String,
    @SerializedName("id") var id: String,
    @SerializedName("test") var test: Boolean,
    @SerializedName("url") var url: String,
    @SerializedName("locale") var locale: String,
    @SerializedName("images") var images: ArrayList<ImageResponse> = arrayListOf(),
    @SerializedName("dates") var dates: DateResponse? = DateResponse(),
    @SerializedName("_embedded") var embedded: EmbeddedVenueResponse? = EmbeddedVenueResponse()
)