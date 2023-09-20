package com.kupesan.domain

import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import com.kupesan.domain.repository.EventRepository
import com.kupesan.domain.usecase.EventUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class EventInteractor(
    private val eventRepository: EventRepository,
    private val dispatcher: CoroutineDispatcher
) : EventUseCase {

    override suspend fun getEventsByCountryCode(countryCode: String): Flow<NetworkResult<List<EventModel>?>> {
        /**
         * In this case there is no need for any additional business logic to do on the response,
         * we could have used [EventRepository] directly in our view model. I just did it this way
         * to showcase how use cases will be done on a clean architecture pattern. :)
         */
        return eventRepository.fetchEventsByCountryCode(countryCode).flowOn(dispatcher)
    }
}