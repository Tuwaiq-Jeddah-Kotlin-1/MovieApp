package com.tuwaiq.movieapp.ui.movie

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tuwaiq.movieapp.data.model.MovieRepository


class MovieViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
    @Assisted private val state: SavedStateHandle,
) : ViewModel() {

    private val currentQuery = state.getLiveData(KEY, VALUE)
    val movies = currentQuery.switchMap { query ->
        if (query.isNotEmpty()) {
            repository.getSearchMovies(query).cachedIn(viewModelScope)
        } else {
            repository.getNowPlayingMovies().cachedIn(viewModelScope)
        }
    }

    fun searchMovies(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val KEY = "current_query"
        private const val VALUE = ""
    }
}