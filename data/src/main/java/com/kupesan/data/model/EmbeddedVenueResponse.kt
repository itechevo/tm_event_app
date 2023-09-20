package com.kupesan.data.model

import com.google.gson.annotations.SerializedName

data class EmbeddedVenueResponse(
    @SerializedName("venues") var venues: ArrayList<VenueResponse> = arrayListOf()
)