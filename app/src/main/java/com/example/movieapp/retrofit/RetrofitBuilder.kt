package com.example.movieapp.retrofit

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 object RetrofitBuilder {
    private val BASE_URL = "http://api.themoviedb.org/3/"
    var retrofit:Retrofit?=null

 fun getInstance():Retrofit?{

    if(retrofit==null) {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    return retrofit
}
}