package com.example.moviesearch.data.model

import com.google.gson.annotations.SerializedName


data class SearchResult(
    @SerializedName("Search") val search: List<Search>,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("Response") val response: Boolean
) {
    data class Search(
        @SerializedName("Title") val title: String,
        @SerializedName("Year") val year: Int,
        @SerializedName("imdbID") val imdbID: String,
        @SerializedName("Type") val type: String,
        @SerializedName("Poster") val poster: String
    )
}
