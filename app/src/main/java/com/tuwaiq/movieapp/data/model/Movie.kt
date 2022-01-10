package com.tuwaiq.movieapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: String,
    val overview : String?,
    val poster_path: String,
    val original_title: String,
    val vote_average: Number

): Parcelable{
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}