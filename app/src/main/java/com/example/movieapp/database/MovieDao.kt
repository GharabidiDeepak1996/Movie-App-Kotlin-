package com.example.movieapp.database

import androidx.room.*
import com.example.movieapp.model.FavItemEntity
import com.example.movieapp.model.MovieEntity

@Dao
interface MovieDao {
//https://stackoverflow.com/questions/44184769/android-room-select-query-with-like

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieData(movieEntities: List<MovieEntity>?)

    @Query("SELECT * FROM moviesentity where original_title LIKE '%' || :movieTitle || '%' ")
    fun searchQuuery(movieTitle: String?): MutableList<MovieEntity>?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavItem(favItemEntity: FavItemEntity)


    @Query("SELECT * FROM FavItemEntity")
    fun fetchFavItem(): MutableList<FavItemEntity>

    @Query("SELECT * FROM FavItemEntity WHERE Movieid =:movieID")
    fun fetechMovieID(movieID: Int): MutableList<FavItemEntity>

    @Query("DELETE FROM favitementity WHERE Movieid =:movieID")
    fun deleteMovieID(movieID: Int)


}