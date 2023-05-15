package com.example.moviesearch.data.repository

import com.example.moviesearch.data.model.MovieDetails
import com.example.moviesearch.data.model.SearchResult
import com.example.moviesearch.data.network.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    private val movieApiService: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetails {
        return movieApiService.getMovieDetails(imdbID = imdbID)
    }

    suspend fun searchMovies(query: String, page: Int): SearchResult {
        return movieApiService.getMovieSearch(query = query, page = page)
    }

    suspend fun loadNextPage(query: String, page: Int): SearchResult {
        return movieApiService.getMovieSearch(query = query, page = page)
    }


}
