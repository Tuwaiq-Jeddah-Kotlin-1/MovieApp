package com.tuwaiq.movieapp.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.remot.MovieRepository

class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    ViewModel() {
    val movies = repository.getNowPlayingMovies()
}