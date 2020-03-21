package com.example.movieapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.constant.constant.MOVIE_NAME
import com.example.movieapp.constant.constant.MOVIE_OVERVIEW
import com.example.movieapp.constant.constant.MOVIE_POSTER
import com.example.movieapp.constant.constant.MOVIE_RATING
import com.example.movieapp.constant.constant.MOVIE_RELEASE_DATE
import com.example.movieapp.constant.constant.ORIGINAL_LANGUAGE

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var intent: Intent

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val image=findViewById(R.id.movieimage) as ImageView
        val title=findViewById(R.id.movietitle) as TextView
        val releasedate=findViewById(R.id.TReleasedate) as TextView
        val overview=findViewById(R.id.Toverview) as TextView
        val language=findViewById(R.id.ToriginalLanguage) as TextView
        val rating=findViewById(R.id.TRating) as TextView


        intent= getIntent()
        val moviePoster:String?=intent.getStringExtra(MOVIE_POSTER)
        val movieTitle:String?=intent.getStringExtra(MOVIE_NAME)
        val movieReleaseDate:String?=intent.getStringExtra(MOVIE_RELEASE_DATE)
        val movieOverView:String?=intent.getStringExtra(MOVIE_OVERVIEW)
        val movieLanguage:String?=intent.getStringExtra(ORIGINAL_LANGUAGE)
        val movieRating:Double?=intent.getDoubleExtra(MOVIE_RATING,1.2)

        val poster = "https://image.tmdb.org/t/p/w500" + moviePoster
        Glide.with(this)
            .load(poster)
            .placeholder(R.drawable.load)
            .into(image)

        title.text=movieTitle
        releasedate.text=movieReleaseDate
        rating.text= movieRating.toString()
        overview.text=movieOverView
        language.text=movieLanguage
    }
}
