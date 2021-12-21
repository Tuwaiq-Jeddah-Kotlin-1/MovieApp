package com.tuwaiq.movieapp.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.remot.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    val movies = repository.getNowPlayingMovies()
}