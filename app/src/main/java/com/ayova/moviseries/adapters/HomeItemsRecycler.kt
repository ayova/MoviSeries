package com.ayova.moviseries.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.tmdb_models.*
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.ayova.moviseries.views.ItemDetailsFragment
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.home_movies_shows_recycler.view.*
import java.lang.Exception

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
        // TODO: set title width = to image width, dynamically

        if (data is DiscoveredMovie) {
            Log.v(TAG, data.toString())
            holder.title.text = data.title
            Picasso.get().load("${TmdbApi.POSTER_URL}${data.poster_path}").into(holder.image)
            // set click listener - i.e. go to details fragment and search for movie details
            holder.image.setOnClickListener {
                holder.activity.supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,
                    ItemDetailsFragment.newInstance(data.id.toString(), ItemDetailsType.movie.toString()))
                    .addToBackStack("ItemDetailsFragment").commit()
            }
        } else if (data is DiscoveredTV) {
            holder.title.text = data.name
            Picasso.get().load("${TmdbApi.POSTER_URL}${data.poster_path}").into(holder.image)
            // set click listener - i.e. go to details fragment and search for tv show details
            holder.image.setOnClickListener {
                holder.activity.supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,
                    ItemDetailsFragment.newInstance(data.id.toString(), ItemDetailsType.tv_show.toString()))
                    .addToBackStack("ItemDetailsFragment").commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = itemView.home_movies_shows_image
        val title: TextView = itemView.home_movies_shows_title
        val activity: AppCompatActivity = v.context as AppCompatActivity

        fun equalWidth(){
            title.maxWidth = image.width
        }


    }
}