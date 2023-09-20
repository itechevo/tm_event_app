package com.kupesan.data.model

import com.google.gson.annotations.SerializedName


data class StartDateResponse(
    @SerializedName("localDate") var localDate: String? = null,
    @SerializedName("localTime") var localTime: String? = null,
    @SerializedName("dateTime") var dateTime: String? = null,
    @SerializedName("dateTBD") var dateTBD: Boolean? = null,
    @SerializedName("dateTBA") var dateTBA: Boolean? = null,
    @SerializedName("timeTBA") var timeTBA: Boolean? = null,
    @SerializedName("noSpecificTime") var noSpecificTime: Boolean? = null
)