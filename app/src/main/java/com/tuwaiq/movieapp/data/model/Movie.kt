package com.tuwaiq.movieapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val overview: String?,
    val poster_path: String,
    val original_title: String,
    val vote_average: Number,
    val release_date: String,
    val original_language: String,
    val popularity: Number,
    val vote_count: Int,
    val adult: Boolean

) : Parcelable{
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}