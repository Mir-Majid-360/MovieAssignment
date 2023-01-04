package com.education.movieassignment.api

import com.education.movieassignment.models.MovieResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("movies")
    suspend fun  getMovies():Response<MovieResponseModel>


}