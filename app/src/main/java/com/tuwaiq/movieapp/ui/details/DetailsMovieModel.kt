package com.tuwaiq.movieapp.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.local.FavoriteMovie
import com.tuwaiq.movieapp.data.local.FavoriteMovieRepository
import com.tuwaiq.movieapp.data.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsMovieModel @ViewModelInject constructor(
    private val repository: FavoriteMovieRepository,
) : ViewModel() {
    fun addToFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.original_title,
                    movie.overview,
                    movie.poster_path,
                    movie.vote_average.toDouble()
                )
            )
        }
    }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

}