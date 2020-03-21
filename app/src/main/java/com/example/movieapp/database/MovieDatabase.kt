package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.model.FavItemEntity
import com.example.movieapp.model.MovieEntity

//https://developer.android.com/training/data-storage/room#kotlin
//https://android--code.blogspot.com/2018/07/android-kotlin-room-database-example.html
@Database(entities = arrayOf(MovieEntity::class,FavItemEntity::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao():MovieDao
    companion object {
        private var INSTANCE: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "MOVIE DATABASE"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as MovieDatabase
        }
    }
}