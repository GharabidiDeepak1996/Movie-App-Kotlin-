package com.example.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.activity.DetailActivity
import com.example.movieapp.constant.constant.MOVIE_NAME
import com.example.movieapp.constant.constant.MOVIE_OVERVIEW
import com.example.movieapp.constant.constant.MOVIE_POSTER
import com.example.movieapp.constant.constant.MOVIE_RATING
import com.example.movieapp.constant.constant.MOVIE_RELEASE_DATE
import com.example.movieapp.constant.constant.ORIGINAL_LANGUAGE
import com.example.movieapp.database.MovieDatabase
import com.example.movieapp.model.FavItemEntity
import com.example.movieapp.model.MovieEntity
import com.google.gson.Gson
import com.sackcentury.shinebuttonlib.ShineButton
import java.util.*

//https://www.simplifiedcoding.net/kotlin-recyclerview-example/
 class MovieListAdapter(var context: Context,var movieList: MutableList<MovieEntity>?) :
    RecyclerView.Adapter<MovieListAdapter.viewHolder>() {
    var movie:Int?=null
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val BROADCAST_FOR_NOTIFY_THE_ADAPTER="boradcast_for_notify_the_adapter"
        val BROADCAST_FOR_NOTIFY_THE_ADAPTER_FOR_DELETE="boradcast_for_notify_the_adapter_for_delete"
        lateinit var intent:Intent

        val imageView = itemView.findViewById(R.id.movieimage) as ImageView
        val movieTitle = itemView.findViewById(R.id.movietitle) as TextView
        val movieRating = itemView.findViewById<TextView>(R.id.movierating)
        val favButton = itemView.findViewById(R.id.fav) as ShineButton

        fun bindItems(movielist: MovieEntity?) {
            movieTitle.text = movielist?.movieTitle
            movieRating.text = movielist?.rating.toString()

            val poster = "https://image.tmdb.org/t/p/w500" + movielist?.poster
            Glide.with(itemView.context)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(imageView)

itemView.setOnClickListener(object :View.OnClickListener{
    override fun onClick(v: View?) {
        intent= Intent(itemView.context,DetailActivity::class.java)
        intent.putExtra(MOVIE_NAME,movielist?.movieTitle)
        intent.putExtra(MOVIE_RELEASE_DATE,movielist?.releaseDate)
        intent.putExtra(MOVIE_RATING,movielist?.rating)
        intent.putExtra(ORIGINAL_LANGUAGE,movielist?.originalLanguage)
        intent.putExtra(MOVIE_OVERVIEW,movielist?.overview)
        intent.putExtra(MOVIE_POSTER,movielist?.poster)
        itemView.context.startActivity(intent)
    }

})
            favButton.setOnCheckStateChangeListener(object : ShineButton.OnCheckedChangeListener {
                override fun onCheckedChanged(view: View?, checked: Boolean) {
                    if (checked == true) {
                        Log.d("MovieAdapter", "button" + movielist?.movieID)
                        val fav = FavItemEntity(
                            movielist?.movieID,
                            movielist?.poster,
                            movielist?.movieTitle,
                            movielist?.rating
                        )

                        val mainDatabase: MovieDatabase = MovieDatabase.getInstance(itemView.context)  // Initialize the room database
                        mainDatabase.movieDao().insertFavItem(fav)  // Put the MovieData in database
                        //creating the broadcast for to notify the adapter
                        val  intent = Intent(BROADCAST_FOR_NOTIFY_THE_ADAPTER)
                        intent.putExtra("MovieID",movielist?.movieID)
                        itemView.context.sendBroadcast(intent)


                    } else {
                        Log.d("MovieAdapter", "button" + checked)

                        val  intent = Intent(BROADCAST_FOR_NOTIFY_THE_ADAPTER_FOR_DELETE)
                        intent.putExtra("MovieID",movielist?.movieID)
                        itemView.context.sendBroadcast(intent)
                        favButton.setChecked(false)

                    }
                }
            })

        }
    }
    fun clearList() {
        movieList?.clear()
    }

    fun notifyForSearchQuery(searchQuery: MutableList<MovieEntity>?) {
        movieList?.addAll(searchQuery!!)
        notifyDataSetChanged()

    }

    fun notifyToAdapter(moreData: MutableList<MovieEntity>?) {

        movieList?.addAll(moreData!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)

        return viewHolder(view)
    }




    override fun getItemCount(): Int {
        return movieList!!.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        // var movie: MovieEntity? = movieList?.get(position)
        holder.bindItems(movieList?.get(position))

        }
    }


