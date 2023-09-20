package com.kupesan.data.model

import com.google.gson.annotations.SerializedName


data class DateResponse(
    @SerializedName("start") var start: StartDateResponse? = StartDateResponse(),
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("status") var status: StatusResponse? = StatusResponse(),
    @SerializedName("spanMultipleDays") var spanMultipleDays: Boolean? = null
)