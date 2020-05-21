package com.ayova.moviseries.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.squareup.picasso.Picasso

class PlaylistDetailsAdapter(val movies: ArrayList<String>) : RecyclerView.Adapter<PlaylistDetailsAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.playlist_details_item, parent, false)
        return MainViewHolder(v)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = movies.get(position)
        Picasso.get().load(data).into(holder.poster)

    }
    override fun getItemCount(): Int {
        return movies.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val poster: ImageView = v.findViewById(R.id.playlist_details_poster)
    }
}