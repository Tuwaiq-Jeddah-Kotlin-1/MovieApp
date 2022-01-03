package com.tuwaiq.movieapp.data.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.tuwaiq.movieapp.api.MovieApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    fun getNowPlayingMovies() =
        Pager(PagingConfig(5, 20, false),
            pagingSourceFactory = { MoviePagingSource(movieApi, null) }).liveData

    fun getSearchMovies(query: String) =
        Pager(PagingConfig(5,20,false),
            pagingSourceFactory = { MoviePagingSource(movieApi, query) }).liveData
}