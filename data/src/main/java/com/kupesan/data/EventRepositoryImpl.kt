package com.kupesan.data

import com.kupesan.data.model.Event
import com.kupesan.data.source.api.TMApiService
import com.kupesan.data.source.local.AppDatabase
import com.kupesan.data.source.local.EventDao
import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import com.kupesan.domain.model.Error
import com.kupesan.domain.model.Success
import com.kupesan.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val tmApiService: TMApiService,
    private val eventDao: EventDao
) : EventRepository {

    /**
     * TODO:
     * Assumption made here to only parse and map what is needed for this task, instead of saving
     * the whole json response. Also decided to use the 4:3 image and ignore others.
     */
    override suspend fun fetchEventsByCountryCode(countryCode: String): Flow<NetworkResult<List<EventModel>?>> =
        flow {
            try {
                //emit if we have cached response
                val cachedDbEvents = eventDao.loadAllByCountryCode(countryCode)
                if (cachedDbEvents.isNotEmpty()) {
                    val cachedEventModels = mapToDomain(cachedDbEvents)

                    emit(Success(data = cachedEventModels))
                }

                //request api response
                val eventsResponse =
                    tmApiService.fetchEventsByCountryCode(countryCode = countryCode)
                val eventList = eventsResponse.embedded?.events?.map { eventResponse ->
                    val imageUrl = eventResponse.images.firstOrNull { it.ratio == "4_3" }?.url
                    Event(
                        id = eventResponse.id,
                        title = eventResponse.name,
                        date = eventResponse.dates?.start?.localDate,
                        imageUrl = imageUrl,
                        location = eventResponse.embedded?.venues?.firstOrNull()?.state?.name,
                        venue = eventResponse.embedded?.venues?.firstOrNull()?.name,
                        countryCode = countryCode
                    )
                }

                var eventResult = emptyList<EventModel>()

                //save to local database
                if (!eventList.isNullOrEmpty()) {
                    eventDao.insertAll(eventList)
                    eventResult = mapToDomain(eventList)
                }

                emit(Success(data = eventResult))
            } catch (t: Throwable) {
                emit(Error(t))
            }
        }.flowOn(dispatcher)

    private fun mapToDomain(events: List<Event>): List<EventModel> {
        return events.map { event ->
            EventModel(
                id = event.id,
                title = event.title,
                date = event.date,
                imageUrl = event.imageUrl,
                location = event.location,
                venue = event.venue,
            )
        }
    }

}