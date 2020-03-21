package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.FavItemEntity

class FavItemAdapter(var faveItem: MutableList<FavItemEntity>) : RecyclerView.Adapter<FavItemAdapter.viewHold>() {

    class viewHold(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieTitle=itemView.findViewById(R.id.fmovietitle) as TextView
        val movieRating=itemView.findViewById(R.id.fmovierating) as TextView
        val moviePoster=itemView.findViewById(R.id.fmovieimage) as ImageView

        fun bindItem(fav: FavItemEntity) {
            movieTitle.text=fav.movieTitle
            movieRating.text= fav.rating.toString()

            val poster = "https://image.tmdb.org/t/p/w500" + fav.poster
            Glide.with(itemView.context)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(moviePoster)
        }
    }
         fun notifyToAdapter( faveItem1:MutableList<FavItemEntity>) {
faveItem.addAll(faveItem1)
notifyDataSetChanged()
         }

fun notifyToAdapterForClear(){
    faveItem.clear()
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHold {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_card_view, parent, false)

        return viewHold(view)
    }

    override fun getItemCount(): Int {
       return faveItem.size
    }

    override fun onBindViewHolder(holder: viewHold, position: Int) {
//val fav:FavItemEntity=faveItem.get(position)
        holder.bindItem(faveItem.get(position))
    }


}