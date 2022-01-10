package com.tuwaiq.movieapp.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.model.MovieRepository

class FavoriteViewModel @ViewModelInject constructor(repository: MovieRepository) : ViewModel() {
    val movies = repository.getFavoriteMovies()
}