# TM Event List App

Android application designed to retrieves event data from a single endpoint and presents it in an
easy-to-navigate list. Additionally, it offers a text search functionality to find specific events
shown in the list.

## Implementation Details

I have used MVVM and clean architecture design pattern. I've divided the project into distinct
modules: DATA (Repositories), DOMAIN (Use cases), and APP (UI).

While this task doesn't necessarily require the use of the DOMAIN layer, I've included it to
illustrate how it can be integrated. We could have directly utilized the repository in the view
model for simplicity.

Android architecture components: ViewModel, Room, Jetpack Compose and Hilt.

Third-party libraries used: Retrofit, GSON, MockK, Coil

I've also added unit tests using MockK for the repository, use cases, and view model, as well
as UI tests for the event screen using Espresso.

## Assumptions

I made the following assumptions and decisions:

* Country code is hardcoded to UK for simplicity.
* Data parsing and mapping are optimized to extract only the required information for this task from
  the JSON response, rather than storing the entire dataset.
* 4:3 image format is selected for display.
* The search functionality operates on the items already displayed to the user, rather than
  performing backend or local database searches.

## TODO

With more time, I would suggest following improvements:

* Implement more robust network availability checks.
* Remove few hardcoded dimensions, colors, and values.
* Enhance error handling to differentiate and handle specific error types such as network issues,
  parsing errors, etc.
* Add support for dark mode.
* Expand both UI and unit testing coverage.