package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class EmbeddedEventResponse(
    @SerializedName("events") var events: ArrayList<EventResponse> = arrayListOf()
)