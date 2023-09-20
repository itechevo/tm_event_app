package com.kupesan.domain.repository

import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun fetchEventsByCountryCode(countryCode: String): Flow<NetworkResult<List<EventModel>?>>
}