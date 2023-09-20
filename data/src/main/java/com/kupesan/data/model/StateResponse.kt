package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class StateResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("stateCode") var stateCode: String? = null
)