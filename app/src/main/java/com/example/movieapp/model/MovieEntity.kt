package com.example.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MoviesEntity",indices =[Index(value = ["Movieid" ], unique = true)])
 class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Transient          //Unable to create converter for class this type of error to handel
    var id: Int?=null

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var poster: String? = null

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String? = null

    @ColumnInfo(name = "Movieid")
    @SerializedName("id")
    var movieID: Int? = null

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String? = null

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var movieTitle: String? = null

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var rating: Double? = null


   @ColumnInfo(name = "originalLanguage")
   @SerializedName("original_language")
    var originalLanguage: String? = null

}