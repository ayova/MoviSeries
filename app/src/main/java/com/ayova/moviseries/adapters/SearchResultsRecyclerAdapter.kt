package com.ayova.moviseries.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.tmdb_models.ItemDetailsType
import com.ayova.moviseries.tmdb_models.SearchedMovie
import com.ayova.moviseries.tmdb_models.SearchedTV
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.ayova.moviseries.views.ItemDetailsFragment
import com.squareup.picasso.Picasso

class SearchResultsRecyclerAdapter(private val itemsFound: ArrayList<Any>?, val actContext: Context) : RecyclerView.Adapter<SearchResultsRecyclerAdapter.MainViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    val TAG = "miappResults"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_result_recycler, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        // Get the list
        val data = itemsFound?.get(position)
        if (data is SearchedMovie){
            holder.title.text = data?.title
            holder.year.text = data?.release_date
            if (data?.poster_path.isNullOrEmpty()){
                Picasso.get()
                    .load("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fnetflixroulette.files.wordpress.com%2F2013%2F01%2Fimage-not-found.gif&f=1&nofb=1")
                    .resize(50,70).centerCrop().into(holder.poster)
            } else {
                Picasso.get().load("${TmdbApi.POSTER_URL}${data?.poster_path}").fit().into(holder.poster)
            }
            holder.card.setOnClickListener {
                Log.v(TAG, "listened to click")
                holder.activity.supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,
                    ItemDetailsFragment.newInstance(data.id.toString(),ItemDetailsType.movie.toString()))
                    .addToBackStack("SearchFragment").commit()
            }
        }
        if (data is SearchedTV){
            holder.title.text = data?.name
            holder.year.text = data?.first_air_date
            if (data?.poster_path.isNullOrEmpty()){
                Picasso.get()
                    .load("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Felitescreens.com%2Fimages%2Fproduct_album%2Fno_image.png&f=1&nofb=1")
                    .into(holder.poster)
            } else {
                Picasso.get().load("${TmdbApi.POSTER_URL}${data?.poster_path}").fit().into(holder.poster)
            }

            holder.card.setOnClickListener {
                Log.v(TAG, "listened to click")
                holder.activity.supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,
                    ItemDetailsFragment.newInstance(data.id.toString(),ItemDetailsType.tv_show.toString()))
                    .addToBackStack("SearchFragment").commit()
            }
        }
    }
    override fun getItemCount(): Int {
        return itemsFound?.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.search_result_recycler_title)
        val year: TextView = v.findViewById(R.id.search_result_recycler_year)
        val poster: ImageView = v.findViewById(R.id.search_result_recycler_poster)
        val activity: AppCompatActivity = v.context as AppCompatActivity
        val card: CardView = v.findViewById(R.id.search_result_recycler_card)
    }
}