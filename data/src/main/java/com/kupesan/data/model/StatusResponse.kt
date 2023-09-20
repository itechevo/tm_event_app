package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("code") var code: String? = null
)