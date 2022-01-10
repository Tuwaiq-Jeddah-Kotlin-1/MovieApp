package com.tuwaiq.movieapp.data.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.tuwaiq.movieapp.api.MovieApi
import com.tuwaiq.movieapp.data.local.FavoriteMovie
import com.tuwaiq.movieapp.data.local.FavoriteMovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi, private val favoriteMovieDao: FavoriteMovieDao) {

    fun getNowPlayingMovies() =
        Pager(PagingConfig(5, 20, false),
            pagingSourceFactory = { MoviePagingSource(movieApi, null) }).liveData

    fun getSearchMovies(query: String) =
        Pager(PagingConfig(5, 20, false),
            pagingSourceFactory = { MoviePagingSource(movieApi, query) }).liveData

    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) =favoriteMovieDao.addToFavorite(favoriteMovie)
    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)
    suspend fun removeFromFavorite(id: String) = favoriteMovieDao.removeFromFavorite(id)
}