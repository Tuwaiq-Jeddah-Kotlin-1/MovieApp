package com.tuwaiq.movieapp.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuwaiq.movieapp.data.local.FavoriteMovie
import com.tuwaiq.movieapp.data.model.Movie
import com.tuwaiq.movieapp.data.model.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsMovieModel @ViewModelInject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    fun addToFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.original_title,
                    movie.overview,
                    movie.poster_path,
                    movie.vote_average.toDouble(),
                    movie.release_date,
                    movie.original_language,
                    movie.popularity.toDouble(),
                    movie.vote_count,
                    movie.adult
                )
            )
        }
    }

    val liveData: MutableLiveData<Boolean> = MutableLiveData()

    fun checkMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.checkMovie(id)
            withContext(Dispatchers.Main) {
                val checked = result > 0
                liveData.value = checked
            }
        }
    }

    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

}