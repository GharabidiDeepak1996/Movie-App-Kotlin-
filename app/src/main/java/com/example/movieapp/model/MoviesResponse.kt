package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

class MoviesResponse {
    @SerializedName("page")
    var page: Int?=null

    @SerializedName("total_results")
    var totalResult: Int ?=null

    @SerializedName("total_pages")
    var totalPages: Int?=null

    @SerializedName("results")
    var results:MutableList<MovieEntity>?=null

}