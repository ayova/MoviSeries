package com.ayova.moviseries.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.home_movies_shows_recycler.view.*

class HomeItemsRecyclerAdapter(private val movies: ArrayList<String>) : RecyclerView.Adapter<HomeItemsRecyclerAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_movies_shows_recycler, parent, false)
        return MainViewHolder(v)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = movies[position]
//        data.let {
//            holder.bindItems(it)
//        }

        Picasso.get().load(data).into(holder.image)
    }
    override fun getItemCount(): Int {
        return movies.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: CircleImageView = itemView.home_movies_shows_image
//        fun bindItems(moviePoster: String) {
//            Log.e("miapp", moviePoster)
//            Picasso.get().load(moviePoster).into(image)
//        }
    }
}