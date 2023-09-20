package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("ratio") var ratio: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("fallback") var fallback: Boolean? = null
)