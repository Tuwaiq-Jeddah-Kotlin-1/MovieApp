package com.tuwaiq.movieapp.data.model

import androidx.paging.PagingSource
import com.tuwaiq.movieapp.api.MovieApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource @Inject constructor(
    private val movieApi: MovieApi,
    private val query: String?,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (query != null) movieApi.searchMovies(query,
                position) else movieApi.getNowPlayingMovies(position)
            val movies = response.results

            LoadResult.Page(movies, if (position == STARTING_PAGE_INDEX) null else position - 1,
                if (movies.isEmpty()) null else position + 1)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }
}