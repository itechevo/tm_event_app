package com.kupesan.data

import com.google.gson.Gson
import com.kupesan.data.model.Event
import com.kupesan.data.model.EventsResponse
import com.kupesan.data.source.api.TMApiService
import com.kupesan.data.source.local.EventDao
import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.Success
import com.kupesan.domain.model.Error
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalCoroutinesApi::class)
class EventRepositoryImplTest {

    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    //region mocks
    private val mockTMApiService = mockk<TMApiService>(relaxed = true)
    private val mockEventDao = mockk<EventDao>(relaxed = true)
    //endregion

    private val sut = EventRepositoryImpl(dispatcher, mockTMApiService, mockEventDao)

    //API response
    private lateinit var apiResponse: EventsResponse

    //DB Result
    private val dbResult =
        List(3) { _ -> Event("id", "Title 1", "Desc 1", "URL 1", "UK", "UK", "Venue") }

    @Before
    fun setUp() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("apiResponse.json")

        val textBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8)).use { reader ->
            var c: Int
            while (reader.read().also { c = it } != -1) {
                textBuilder.append(c.toChar())
            }
        }
        apiResponse = Gson().fromJson(textBuilder.toString(), EventsResponse::class.java)
    }

    @Test
    fun `given success when fetchEventsByCountryCode then return api response`() =
        runTest {

            //given
            coEvery { mockTMApiService.fetchEventsByCountryCode(countryCode = any()) } returns apiResponse
            every { mockEventDao.insertAll(any()) } just runs

            //when
            val result = sut.fetchEventsByCountryCode("UK").toList()

            //then
            coVerify { mockTMApiService.fetchEventsByCountryCode(countryCode = any()) }
            verify { mockEventDao.insertAll(any()) }

            assertEquals(1, result.size)
            val events = result.first() as Success<List<EventModel>?>
            //Result has 20 events as sample json
            assertEquals(apiResponse.embedded?.events?.size, events.data?.size)
        }

    @Test
    fun `given error when fetchEventsByCountryCode then return error`() =
        runTest {

            //given
            coEvery { mockTMApiService.fetchEventsByCountryCode(countryCode = any()) } throws Throwable()

            //when
            val result = sut.fetchEventsByCountryCode("UK").toList()

            //then
            coVerify { mockTMApiService.fetchEventsByCountryCode(countryCode = any()) }

            assertEquals(1, result.size)
            assertTrue(result.first() is Error)
        }

    @Test
    fun `given cached events when fetchEventsByCountryCode then return cached response before API response`() =
        runTest {

            //given
            coEvery { mockTMApiService.fetchEventsByCountryCode(countryCode = any()) } returns apiResponse
            every { mockEventDao.insertAll(any()) } just runs
            every { mockEventDao.loadAllByCountryCode(any()) } returns dbResult

            //when
            val result = sut.fetchEventsByCountryCode("UK").toList()

            //then
            assertEquals(2, result.size)

            //cached result
            val cachedResult = result.first() as Success<List<EventModel>?>
            assertEquals(dbResult.size, cachedResult.data?.size)

            //api result
            val apiResult = result[1] as Success<List<EventModel>?>
            assertEquals(apiResponse.embedded?.events?.size, apiResult.data?.size)
        }
}