package com.kupesan.tmapp.ui.screen.event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kupesan.domain.model.EventModel
import com.kupesan.tmapp.R
import com.kupesan.tmapp.ui.component.EventCard
import com.kupesan.tmapp.ui.component.SearchBar
import com.kupesan.tmapp.ui.model.ErrorType
import com.kupesan.tmapp.ui.theme.Purple40
import kotlinx.coroutines.launch


@Stable
class EventsScreenState(
    val eventsUiState: EventsUiState
)

@Composable
fun rememberEventsScreenState(
    eventsUiState: EventsUiState
): EventsScreenState = remember {
    EventsScreenState(
        eventsUiState = eventsUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    screenState: EventsScreenState = rememberEventsScreenState(
        eventsUiState = viewModel.uiState
    )
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var snackbarShowing by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.event_screen_title), color = White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40)
            )
        }
    ) { contentPadding ->
        Box(
            Modifier.padding(contentPadding),
        ) {
            EventsContent(screenState, onRetryClick = {
                viewModel.fetchEvents()
            }, onSearch = {
                viewModel.search(it)
            }, showSnackBar = {
                if (!snackbarShowing) {
                    snackbarShowing = true
                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = context.getString(R.string.offline_msg),
                                actionLabel = context.getString(R.string.refresh),
                                duration = SnackbarDuration.Indefinite
                            )
                        snackbarShowing = when (result) {
                            SnackbarResult.ActionPerformed -> {
                                viewModel.fetchEvents()
                                false
                            }

                            SnackbarResult.Dismissed -> {
                                false
                            }
                        }
                    }
                }
            })
        }
    }
}

@Composable
fun EventsContent(
    screenState: EventsScreenState,
    onRetryClick: () -> Unit,
    onSearch: (String) -> Unit,
    showSnackBar: () -> Unit
) {
    if (screenState.eventsUiState.loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (screenState.eventsUiState.errorType == ErrorType.Offline) {
            showSnackBar()
        }
        Column {
            SearchBar { onSearch(it) }
            if (screenState.eventsUiState.errorType == ErrorType.Generic) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.generic_error),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = { onRetryClick() }
                    ) {
                        Text(stringResource(R.string.try_again))
                    }
                }
            } else if (screenState.eventsUiState.events.isEmpty()) {
                Text(
                    stringResource(R.string.no_event_msg), modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Spacer(modifier = Modifier.padding(top = 4.dp)) }

                    items(screenState.eventsUiState.events,
                        key = {
                            it.id
                        }) { event ->
                        EventCard(event)
                    }

                    item { Spacer(modifier = Modifier.padding(top = 4.dp)) }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    EventsContent(
        rememberEventsScreenState(object : EventsUiState {
            override val loading: Boolean
                get() = false
            override val errorType: ErrorType
                get() = ErrorType.None
            override val events: List<EventModel>
                get() = listOf(
                    EventModel(
                        id = "XYZ1",
                        title = "Hello there!",
                        date = "Sep 30",
                        null, null, null
                    ), EventModel(
                        id = "XYZ1",
                        title = "Not now!",
                        date = "Sep 21",
                        null, null, null
                    )
                )

        }), {}, {}, {}
    )
}