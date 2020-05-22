package com.ayova.moviseries.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.squareup.picasso.Picasso

class UserPlaylistAdapter(private val playlist: UserPlaylist?) : RecyclerView.Adapter<UserPlaylistAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.playlist_details_item, parent, false)
        Log.v("miappRv", playlist?.listName.toString())
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.e("miapprv", "${playlist?.tvShowsAdded?.toString()}")
        Picasso.get().load("${playlist?.tvShowsAdded?.toString()}").into(holder.poster)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val poster: ImageView = v.findViewById(R.id.playlist_details_poster)
    }
}