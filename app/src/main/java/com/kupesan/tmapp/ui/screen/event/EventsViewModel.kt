package com.kupesan.tmapp.ui.screen.event

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import com.kupesan.domain.model.Success
import com.kupesan.domain.model.Error
import com.kupesan.domain.usecase.EventUseCase
import com.kupesan.tmapp.ui.model.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
interface EventsUiState {
    val loading: Boolean
    val errorType: ErrorType
    val events: List<EventModel>
}

private class MutableEventsUiState : EventsUiState {
    override var loading: Boolean by mutableStateOf(true)
    override var errorType: ErrorType by mutableStateOf(ErrorType.None)
    override var events: List<EventModel> by mutableStateOf(emptyList())
}

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventUseCase: EventUseCase) : ViewModel() {

    private val _uiState = MutableEventsUiState()
    val uiState: EventsUiState = _uiState

    private var eventResult: List<EventModel> = emptyList()

    init {
        fetchEvents()
    }

    fun fetchEvents() {
        viewModelScope.launch {
            _uiState.errorType = ErrorType.None
            _uiState.loading = true
            //FIXME - For now country code is hardcoded to UK
            eventUseCase.getEventsByCountryCode("UK").collect {
                handleResult(it)
            }
        }
    }

    /**
     * Assumption made here to search what we have shown to the user instead of searching
     * from backend or local database.
     */
    fun search(query: String) {
        if (query.isNotEmpty()) {
            val searchResult =
                eventResult.filter { it.title.lowercase().contains(query.lowercase()) }
            _uiState.events = searchResult
        } else {
            _uiState.events = eventResult
        }
    }

    private fun handleResult(result: NetworkResult<List<EventModel>?>) {
        when (result) {
            is Error -> {
                /**
                 * FIXME:
                 * Currently handling error generically, improve to handle specific errors like network
                 * parsing, etc
                 */
                if (eventResult.isNotEmpty()) {
                    _uiState.errorType = ErrorType.Offline
                } else {
                    _uiState.errorType = ErrorType.Generic
                }
                _uiState.loading = false
            }

            is Success -> {
                eventResult = result.data ?: emptyList()
                _uiState.events = eventResult
                _uiState.loading = false
                _uiState.errorType = ErrorType.None
            }
        }
    }
}