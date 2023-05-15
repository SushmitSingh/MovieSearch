package com.example.moviesearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.data.model.SearchResult
import com.example.moviesearch.data.repository.MovieRepository
import com.example.moviesearch.util.NetworkState
import kotlinx.coroutines.launch

class SearchResultsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchResult.Search>>()
    val searchResults: LiveData<List<SearchResult.Search>> = _searchResults

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState
    private var currentPage = MutableLiveData<Int>()

    // Initialize the current page to 1
    init {
        currentPage.value = 1
    }

    fun getCurrentPage(): LiveData<Int> {
        return currentPage
    }


    private var totalPages = 1
    private var currentQuery = "Rambo"

    init {
        searchMovies(currentQuery, 1)
    }

    fun searchMovies(query: String, page: Int) {
        currentQuery = query
        currentPage.value = page
        viewModelScope.launch {
            _networkState.postValue(NetworkState.LOADING)
            try {
                val searchResult = repository.searchMovies(query, page)
                val totalResults = searchResult.totalResults
                val resultsPerPage = searchResult.search.size
                // calculate the total pages
                totalPages = if (totalResults % resultsPerPage == 0) {
                    totalResults / resultsPerPage
                } else {
                    (totalResults / resultsPerPage) + 1
                }
                _searchResults.postValue(searchResult.search)
                _networkState.postValue(NetworkState.LOADED)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.error("Failed to search movies"))
            }
        }
    }


    fun loadNextPage() {
        val nextPage = currentPage.value?.plus(1) ?: 1
        searchMovies(currentQuery, nextPage)
        currentPage.value = nextPage
    }

    fun loadPreviousPage() {
        val previousPage = currentPage.value?.minus(1) ?: 1
        searchMovies(currentQuery, previousPage)
        currentPage.value = previousPage
    }


    fun refresh() {
        searchMovies(currentQuery, currentPage.value ?: 1)
    }
}
