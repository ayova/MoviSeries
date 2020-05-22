package com.ayova.moviseries.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.ayova.moviseries.tmdb_models.ItemDetailsType
import com.ayova.moviseries.views.ItemDetailsFragment
import com.ayova.moviseries.views.UserPlaylistFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class UserPlaylistAdapter(private val playlist: UserPlaylist?) : RecyclerView.Adapter<UserPlaylistAdapter.MainViewHolder>() {

    private var items: ArrayList<Map<String, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.playlist_details_item, parent, false)
        items.clear()
        if (playlist?.moviesAdded != null) {
//            items.addAll(playlist?.moviesAdded as ArrayList<Map<String, String>>)
            for (movie in playlist.moviesAdded!!) {
                items.add(mapOf("id" to movie["id"]!!, "image" to movie.get("image")!!, "type" to ItemDetailsType.movie.toString()))
            }
        }
        if (playlist?.tvShowsAdded != null) {
//            items.addAll(playlist?.tvShowsAdded as ArrayList<Map<String, String>>)
            for (tv in playlist.tvShowsAdded!!) {
                items.add(mapOf("id" to tv["id"]!!, "image" to tv.get("image")!!, "type" to ItemDetailsType.tv_show.toString()))
            }
        }
        Log.v("miappRv", items.size.toString())
        Log.v("miappRv", items.toString())
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = items[position]
        Picasso.get().load(data["image"]).into(holder.poster)
        holder.poster.setOnClickListener {
            holder.activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame_container, ItemDetailsFragment.newInstance(data["id"].toString(),data["type"].toString()))
                .addToBackStack("UserPlaylist").commit()
        }
    }

    override fun getItemCount(): Int {
        var total = 0
        if (playlist?.moviesAdded?.size != null){
            total += playlist?.moviesAdded?.size!!
        }
        if (playlist?.tvShowsAdded?.size != null){
            total += playlist?.tvShowsAdded?.size!!
        }
        return total
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val poster: ImageView = v.findViewById(R.id.playlist_details_poster)
        val activity: AppCompatActivity = itemView.context as AppCompatActivity
    }
}