package com.tuwaiq.movieapp.data.remot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(pageSize = 5, maxSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(movieApi) }
        ).liveData
}