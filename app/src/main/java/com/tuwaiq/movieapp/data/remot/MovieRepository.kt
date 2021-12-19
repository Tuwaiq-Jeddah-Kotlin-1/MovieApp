package com.tuwaiq.movieapp.data.remot

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

}