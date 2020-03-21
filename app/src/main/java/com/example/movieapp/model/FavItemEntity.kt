package com.example.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "FavItemEntity",indices =[Index(value = ["Movieid" ], unique = true)])
class FavItemEntity {
    constructor( movieID: Int?, poster: String?, movieTitle: String?, rating: Double?) {
        this.movieID = movieID
        this.poster = poster
        this.movieTitle = movieTitle
        this.rating = rating
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int?=null

    @ColumnInfo(name = "Movieid")
    var movieID: Int? = null

    @ColumnInfo(name = "poster_path")
    var poster: String? = null

    @ColumnInfo(name = "original_title")
    var movieTitle: String? = null

    @ColumnInfo(name = "vote_average")
    var rating: Double? = null
}