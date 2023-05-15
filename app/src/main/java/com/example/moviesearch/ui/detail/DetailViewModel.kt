package com.example.moviesearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.data.model.MovieDetails
import com.example.moviesearch.data.repository.MovieRepository
import com.example.moviesearch.util.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movie = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails>
        get() = _movie

    fun getMovieDetail(imdbId: String) {
        viewModelScope.launch {
            _networkState.value = NetworkState.LOADING
            try {
                val movie = withContext(Dispatchers.IO) {
                    repository.getMovieDetails(imdbId)
                }
                _movie.postValue( movie)
                _networkState.value = NetworkState.LOADED
            } catch (e: Exception) {
                _networkState.value = NetworkState.error(e.message)
            }
        }
    }
}
