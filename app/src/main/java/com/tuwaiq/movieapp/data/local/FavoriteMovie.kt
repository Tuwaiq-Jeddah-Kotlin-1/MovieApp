package com.tuwaiq.movieapp.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_movie")
@Parcelize
data class FavoriteMovie(
    var id_movie: String,
    val original_title: String,
    val overview: String?,
    val poster_path: String?,
    val vote_average: Double,
    val release_date: String,
    val original_language: String,
    val popularity: Double,
    val vote_count: Int,
    val adult: Boolean

) : Serializable, Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}