package com.ayova.moviseries.adapters

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.models.DiscoveredMovie
import com.ayova.moviseries.models.DiscoveredTV
import com.ayova.moviseries.models.HomeList
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.home_movies_shows_recycler.view.*

/**
 * Recycler for each item inside each list in the Home fragment
 * Child of HomeListsRecycler
 */
class HomeItemsRecycler(private val listOfItems: ArrayList<Any>) : RecyclerView.Adapter<HomeItemsRecycler.MainViewHolder>() {

    private val TAG = "miappChildRV"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_movies_shows_recycler, parent, false)
        return MainViewHolder(v)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listOfItems[position]
        holder.title.maxWidth = holder.image.width
        Log.v(TAG, data.toString())
        if (data is DiscoveredMovie) {
            Log.v(TAG, data.toString())
            holder.title.text = data.title
            Picasso.get().load("${TmdbApi.POSTER_URL}${data.poster_path}").into(holder.image)
        } else if (data is DiscoveredTV) {
            holder.title.text = data.name
            Picasso.get().load("${TmdbApi.POSTER_URL}${data.poster_path}").into(holder.image)
        }
    }
    override fun getItemCount(): Int {
        return listOfItems.size
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = itemView.home_movies_shows_image
        val title: TextView = itemView.home_movies_shows_title
    }
}