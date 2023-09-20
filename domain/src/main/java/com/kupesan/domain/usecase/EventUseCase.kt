package com.kupesan.domain.usecase

import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface EventUseCase {

    suspend fun getEventsByCountryCode(countryCode: String): Flow<NetworkResult<List<EventModel>?>>
}