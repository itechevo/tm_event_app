package com.kupesan.domain

import com.kupesan.domain.model.Success
import com.kupesan.domain.model.Error
import com.kupesan.domain.model.EventModel
import com.kupesan.domain.repository.EventRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventInteractorTest {

    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    //region mocks
    private val mockEventRepository = mockk<EventRepository>(relaxed = true)
    //endregion

    private val sut = EventInteractor(mockEventRepository, dispatcher)

    private val validResult =
        Success(data = listOf(EventModel("", "", "", "", "", "")))

    @Test
    fun `given result when getEventsByCountryCode then return valid results`() =
        runTest {

            //given
            coEvery { mockEventRepository.fetchEventsByCountryCode(any()) } returns flowOf(
                validResult
            )

            //when
            val result = sut.getEventsByCountryCode("UK").toList()

            //then
            coVerify { mockEventRepository.fetchEventsByCountryCode(any()) }
            assertEquals(1, result.size)
            assertTrue(result.first() is Success)
            val events = result.first() as Success
            assertEquals(1, events.data?.size)
        }

    @Test
    fun `given error when getEventsByCountryCode then return error`() =
        runTest {

            //given
            coEvery { mockEventRepository.fetchEventsByCountryCode(any()) } returns flowOf(
                Error(
                    Throwable()
                )
            )

            //when
            val result = sut.getEventsByCountryCode("UK").toList()

            //then
            coVerify { mockEventRepository.fetchEventsByCountryCode(any()) }
            assertEquals(1, result.size)
            assertTrue(result.first() is Error)
        }
}