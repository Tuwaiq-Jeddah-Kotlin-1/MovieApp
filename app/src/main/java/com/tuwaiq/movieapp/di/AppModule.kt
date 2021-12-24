package com.tuwaiq.movieapp.di

import android.content.Context
import androidx.room.Room
import com.tuwaiq.movieapp.data.local.FavoriteMovieDatabase
import com.tuwaiq.movieapp.data.remot.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(MovieApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideFavMovieDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app,FavoriteMovieDatabase::class.java, "movie_db").build()

    @Provides
    @Singleton
    fun provideFavMovieDao(db:FavoriteMovieDatabase) = db.getFavoriteMovieDao()
}