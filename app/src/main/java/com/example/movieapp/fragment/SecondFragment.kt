package com.example.movieapp.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.FavItemAdapter
import com.example.movieapp.database.MovieDatabase
import com.example.movieapp.model.FavItemEntity

class SecondFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager:LinearLayoutManager
    lateinit var  movieDatabase:MovieDatabase
    lateinit var faveItem:MutableList<FavItemEntity>
    lateinit var faveItemAdapter:FavItemAdapter
    val BROADCAST_FOR_NOTIFY_THE_ADAPTER="boradcast_for_notify_the_adapter"
    val BROADCAST_FOR_NOTIFY_THE_ADAPTER_FOR_DELETE="boradcast_for_notify_the_adapter_for_delete"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toNotifyUpdateInflater=IntentFilter(BROADCAST_FOR_NOTIFY_THE_ADAPTER)
        context!!.registerReceiver(broadcastforNotifyToAdapter, toNotifyUpdateInflater)

        val toNotifyDeleteInfelater=IntentFilter(BROADCAST_FOR_NOTIFY_THE_ADAPTER_FOR_DELETE)
        context!!.registerReceiver(broadcastforNotifyToDelete, toNotifyDeleteInfelater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
       val v:View=inflater.inflate(R.layout.fragment_second, container, false)

        recyclerView=v.findViewById(R.id.favItem)
        linearLayoutManager = GridLayoutManager(context,2)
        recyclerView.setLayoutManager(linearLayoutManager)

         movieDatabase= MovieDatabase.getInstance(context!!)
        faveItem=movieDatabase.movieDao().fetchFavItem()

        Log.d("SecondF","hello"+faveItem.size)

         faveItemAdapter= FavItemAdapter(faveItem)
         recyclerView.adapter=faveItemAdapter
        return v
    }
val broadcastforNotifyToAdapter=object :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val MovieId:Int=intent!!.getIntExtra("MovieID",0)

        movieDatabase= MovieDatabase.getInstance(context!!)
        val faveItem1:MutableList<FavItemEntity> = movieDatabase.movieDao().fetechMovieID(MovieId)
        Log.d("broadcast","receiver"+faveItem1)
        faveItemAdapter.notifyToAdapter(faveItem1)     //we need broadcast beacuse of to notify the adapter
    }
}


    val broadcastforNotifyToDelete=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val MovieId:Int=intent!!.getIntExtra("MovieID",0)
            movieDatabase.movieDao().deleteMovieID(MovieId)

            val faveItem1:MutableList<FavItemEntity> = movieDatabase.movieDao().fetchFavItem()
            faveItemAdapter.notifyToAdapterForClear()
            faveItemAdapter.notifyToAdapter(faveItem1)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(broadcastforNotifyToAdapter)
        context!!.unregisterReceiver(broadcastforNotifyToDelete)
    }
}
