package com.kupesan.data.model

import com.google.gson.annotations.SerializedName


data class CountryResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("countryCode") var countryCode: String? = null
)