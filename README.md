# MovieSearch

## Overview

The Movie Search App is an Android application developed using the MVVM architecture, Retrofit for REST API integration, ViewBinding for efficient UI binding, and Navigation Component for navigation between screens. The app allows users to search for movies, view a paginated list of results, and access detailed information about each movie.

## Screenshots

### Movie List Screen

![Movie List](WhatsApp%20Image%202024-01-24%20at%2017.25.59.jpeg)
*Movie List: Users can search for movies and view a paginated list of results.*

### Movie Detail Screen

![Movie Detail](WhatsApp%20Image%202024-01-24%20at%2017.15.20.jpeg)
*Movie Detail: Users can access detailed information about each movie.*

## Features

- **MVVM Architecture:** Utilizes the Model-View-ViewModel architecture for a clean and modular code structure.

- **Movie Search:** Users can search for movies by entering keywords.

- **Pagination:** Implements pagination for displaying a large number of movie results.

- **Movie Detail Page:** Provides a detailed view of each selected movie.

- **REST API Integration:** Uses Retrofit for efficient communication with the movie database.

- **Network State Handling:** Manages network states to provide a seamless user experience.

- **ViewBinding:** Utilizes ViewBinding for efficient and type-safe UI binding.

- **Plugins:**
    - `com.android.application`
    - `org.jetbrains.kotlin.android`
    - `kotlin-kapt`
    - `androidx.navigation.safeargs`

## Project Structure

The project structure includes the following main components:

1. **MovieListFragment:** Displays a paginated list of movies based on the search query.

2. **MovieDetailFragment:** Shows detailed information about a selected movie.

3. **MovieViewModel:** Manages UI-related data and communication between the UI and repository.

4. **MovieRepository:** Handles data operations, including fetching data from the network.

5. **Retrofit:** Used for making network requests to the movie database.

6. **Glide:** Used for efficient loading and caching of movie images.

7. **Coroutines:** Uses Kotlin Coroutines for asynchronous programming.

## Usage

1. **Clone the repository:**

    ```bash
    git clone https://github.com/SushmitSingh/MovieSearch.git
    ```

2. **Open the project in Android Studio.**

3. **Build and run the app on your Android device or emulator.**

4. **Search for Movies:** Use the search feature on the Movie List screen to find movies.

5. **View Movie Details:** Tap on a movie in the list to view detailed information.

