package com.kupesan.data.source.api

import com.kupesan.data.model.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMApiService {

    @GET("events.json")
    suspend fun fetchEventsByCountryCode(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("countryCode") countryCode: String
    ): EventsResponse

    companion object {
        const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
        const val API_KEY = "DW0E98NrxUIfDDtNN7ijruVSm60ryFLX"
    }
}