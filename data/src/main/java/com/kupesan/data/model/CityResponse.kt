package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("name") var name: String? = null
)