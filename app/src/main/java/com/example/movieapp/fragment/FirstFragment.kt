package com.example.movieapp.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.activity.DetailActivity
import com.example.movieapp.adapter.MovieListAdapter
import com.example.movieapp.constant.constant
import com.example.movieapp.constant.constant.THE_MOVIE_DB_API_TOKEN
import com.example.movieapp.database.MovieDatabase
import com.example.movieapp.model.MovieEntity
import com.example.movieapp.model.MoviesResponse
import com.example.movieapp.retrofit.MovieAPI
import com.example.movieapp.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class FirstFragment : Fragment() {
    lateinit var movielist: MovieListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var mainDatabase: MovieDatabase
    lateinit var intent:Intent
    var isScrolling: Boolean = false
    var movie: MutableList<MovieEntity>? =null
    var isSearchBarOpen=true
    var page: Int = 1
    val BROADCAST_FOR_MOVIES_SEARCHBAR = "broadcast_for_movie_searchbar"
    val BROADCAST_FOR_MOVIES_SEARCHBAR_CLOSER="broadcast_for_movie_searchbar_closer"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchBarOpenIntentFilter = IntentFilter(BROADCAST_FOR_MOVIES_SEARCHBAR)
        context!!.registerReceiver(broadcastforSearchbarOpen, searchBarOpenIntentFilter)

        val searchBarCloseIntentFilter = IntentFilter(BROADCAST_FOR_MOVIES_SEARCHBAR_CLOSER)
        context!!.registerReceiver(broadcastforSearchbarClose, searchBarCloseIntentFilter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        recyclerView = view.findViewById(R.id.firstfragment_recyclerview)
        val linearLayoutManager = GridLayoutManager(context, 2) //bydefault vertical
        recyclerView.setLayoutManager(linearLayoutManager)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isScrolling = true

                var currentItem: Int = linearLayoutManager.childCount
                var scrolloutItem: Int = linearLayoutManager.findFirstVisibleItemPosition()
                var totalItem: Int = linearLayoutManager.itemCount
                if (isScrolling && isSearchBarOpen && (currentItem + scrolloutItem == totalItem)) {
                    loadMoreData()
                    isScrolling = false
                }
            }
        })
        fetchData()
        return view
    }


    fun fetchData() {
        val apiserive = RetrofitBuilder.getInstance()
        val call = apiserive?.create(MovieAPI::class.java)
        call!!.getPopularMovies(THE_MOVIE_DB_API_TOKEN, page).enqueue(object :
            Callback<MoviesResponse?> {
            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                Log.d("main1", "sdb1" + t.message)
            }

            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if (response.code() == 200) {
                    val movie: MutableList<MovieEntity>? = response.body()?.results

                    movielist = MovieListAdapter(context!!,movie)
                    recyclerView.adapter = movielist

                 mainDatabase=MovieDatabase.getInstance(context!!)  // Initialize the room database
                 mainDatabase.movieDao().insertAllMovieData(movie)  // Put the MovieData in database
                    Log.d("hdfb", "fkdsb" + movie?.size)
                }

            }

        })
    }

    fun loadMoreData() {
        val apiserive = RetrofitBuilder.getInstance()
        val call = apiserive?.create(MovieAPI::class.java)
        call!!.getPopularMovies(THE_MOVIE_DB_API_TOKEN, page)!!.enqueue(object :
            Callback<MoviesResponse?> {
            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if (response.code() == 200) {
                     movie = response.body()?.results
                    mainDatabase=MovieDatabase.getInstance(context!!)  // Initialize the room database
                    mainDatabase.movieDao().insertAllMovieData(movie)  // Put the MovieData in database
                    page++
                    movielist.notifyToAdapter(movie)

                }
            }
        })
    }

    val broadcastforSearchbarOpen = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            isSearchBarOpen =false
            val searchedValues: String? = intent!!.getStringExtra(constant.SEARCH_QUERY)
            mainDatabase=MovieDatabase.getInstance(context!!)
            val returnSumValues: MutableList<MovieEntity>?=mainDatabase.movieDao().searchQuuery(searchedValues)

            movielist.clearList()
            movielist.notifyForSearchQuery(returnSumValues)
            //Log.d("Fragment", "Answare" + returnSumValues)
        }
    }

    val broadcastforSearchbarClose=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            isSearchBarOpen =true
            fetchData()
Log.d("Fragment","receiveornot"+isSearchBarOpen)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(broadcastforSearchbarOpen)
        context!!.unregisterReceiver(broadcastforSearchbarClose)
    }
}
