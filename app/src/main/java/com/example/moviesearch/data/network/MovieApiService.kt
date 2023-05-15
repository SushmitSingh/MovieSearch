package com.example.moviesearch.data.network

import com.example.moviesearch.data.model.MovieDetails
import com.example.moviesearch.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    companion object {
        const val OMDB_ID = "b9bd48a6"
    }

    @GET(".")
    suspend fun getMovieSearch(
        @Query("page") page: Int,
        @Query("s") query: String,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apikey") apikey: String = OMDB_ID
    ): SearchResult

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apiKey") apiKey: String = OMDB_ID,
        @Query("i") imdbID: String,
    ): MovieDetails
}
