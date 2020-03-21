package com.example.movieapp.retrofit

import com.example.movieapp.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
     fun getPopularMovies(@Query("api_key") apiKey: String?, @Query("page") page:Int?): Call<MoviesResponse?>
}