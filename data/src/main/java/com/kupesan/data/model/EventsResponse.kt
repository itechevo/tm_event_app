package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("_embedded") var embedded: EmbeddedEventResponse? = EmbeddedEventResponse()
)