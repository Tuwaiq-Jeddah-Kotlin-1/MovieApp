package com.tuwaiq.movieapp.ui.details

import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.local.FavoriteMovie
import com.tuwaiq.movieapp.data.local.FavoriteMovieRepository
import com.tuwaiq.movieapp.data.remot.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: FavoriteMovieRepository) :
    ViewModel() {
    fun addToFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(movie.id, movie.overview, movie.original_title, movie.poster_path)
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