package com.kupesan.tmapp.ui.screen.event

import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.Success
import com.kupesan.domain.model.Error
import com.kupesan.domain.usecase.EventUseCase
import com.kupesan.tmapp.ui.model.ErrorType
import com.kupesan.tmapp.ui.screen.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    //region mocks
    private val mockEventUseCase = mockk<EventUseCase>()
    //endregion

    private lateinit var eventsViewModel: EventsViewModel

    private val events = listOf(
        EventModel("", "abc", "", "", "", ""),
        EventModel("", "abcd", "", "", "", ""),
        EventModel("", "zxy", "", "", "", "")
    )

    @Before
    fun setUp() {
        //given
        coEvery { mockEventUseCase.getEventsByCountryCode(any()) } returns flowOf(Success(events))

        eventsViewModel = EventsViewModel(mockEventUseCase)
    }

    @Test
    fun `given success when init fetchEvents then return events`() = runTest {
        //given setup called

        //then
        assertEquals(ErrorType.None, eventsViewModel.uiState.errorType)
        assertFalse(eventsViewModel.uiState.loading)
        assertEquals(events.size, eventsViewModel.uiState.events.size)
    }

    @Test
    fun `given error when fetchEvents then return error`() = runTest {
        //given
        coEvery { mockEventUseCase.getEventsByCountryCode(any()) } returns flowOf(Error(Throwable()))
        eventsViewModel = EventsViewModel(mockEventUseCase)

        //when
        eventsViewModel.fetchEvents()

        testScheduler.advanceUntilIdle()

        //then
        assertEquals(ErrorType.Generic, eventsViewModel.uiState.errorType)
        assertFalse(eventsViewModel.uiState.loading)
        assertEquals(0, eventsViewModel.uiState.events.size)
    }

    @Test
    fun `given error and has offline data when fetchEvents then return offline error and events`() =
        runTest {
            //given
            coEvery { mockEventUseCase.getEventsByCountryCode(any()) } returns flowOf(
                Error(
                    Throwable()
                )
            )

            //when
            eventsViewModel.fetchEvents()

            testScheduler.advanceUntilIdle()

            //then
            assertEquals(ErrorType.Offline, eventsViewModel.uiState.errorType)
            assertFalse(eventsViewModel.uiState.loading)
            assertEquals(events.size, eventsViewModel.uiState.events.size)
        }

    @Test
    fun `given 2 events when search then return search results`() =
        runTest {
            //given setup called

            //when
            eventsViewModel.search("abc")

            testScheduler.advanceUntilIdle()

            //then
            assertEquals(ErrorType.None, eventsViewModel.uiState.errorType)
            assertFalse(eventsViewModel.uiState.loading)
            assertEquals(2, eventsViewModel.uiState.events.size)
        }
}