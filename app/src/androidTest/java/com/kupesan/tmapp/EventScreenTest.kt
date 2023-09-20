package com.kupesan.tmapp

import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kupesan.domain.model.EventModel
import com.kupesan.tmapp.ui.screen.event.EventsScreen
import com.kupesan.tmapp.ui.screen.event.EventsViewModel
import com.kupesan.tmapp.ui.theme.TMAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clearAndSetContent(
    content: @Composable () -> Unit
) {
    (this.activity.findViewById<ViewGroup>(android.R.id.content)
        ?.getChildAt(0) as? ComposeView)?.setContent(content)
        ?: this.setContent(content)
}

@HiltAndroidTest
class EventScreenTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var eventsViewModel: EventsViewModel

    @Before
    fun setup() {
        hiltTestRule.inject()

        eventsViewModel = composeTestRule.activity.viewModels<EventsViewModel>().value

        composeTestRule.clearAndSetContent {
            TMAppTheme {
                EventsScreen(eventsViewModel)
            }
        }
    }

    @Test
    fun given_no_events_show_no_event_message() {
        //given
        FakeUseCaseModule.results = emptyList()

        //when
        eventsViewModel.fetchEvents()

        //then
        composeTestRule.onNodeWithText("No events found!").assertIsDisplayed()
    }

    @Test
    fun given_events_show_event_in_list() {
        //given
        FakeUseCaseModule.results = listOf(
            EventModel("1", "Test Event 1", "", "", "", ""),
            EventModel("2", "Test Event 2", "", "", "", "")
        )

        //when
        eventsViewModel.fetchEvents()

        //then
        composeTestRule.onNodeWithText("Test Event 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Event 2").assertIsDisplayed()
    }
}