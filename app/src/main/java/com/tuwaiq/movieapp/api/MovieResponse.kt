package com.tuwaiq.movieapp.api

import com.tuwaiq.movieapp.data.model.Movie

data class MovieResponse(
    val results: List<Movie>,
)