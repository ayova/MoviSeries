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
//        items
        Log.v("miappRv", playlist?.listName.toString())
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = playlist?.moviesAdded?.get(position)
//        Log.e("miapprv", "${playlist?.tvShowsAdded?.toString()}")
        Picasso.get().load(data?.get("image")).into(holder.poster)
        holder.poster.setOnClickListener {
            Log.v("miappclick", "poster")
            holder.activity.supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,
                ItemDetailsFragment.newInstance(data?.getValue("id").toString(), ItemDetailsType.movie.toString()))
                .addToBackStack("UserPlaylist").commit()
        }
    }

    override fun getItemCount(): Int {
        return playlist?.moviesAdded?.size ?: 0
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val poster: ImageView = v.findViewById(R.id.playlist_details_poster)
        val activity: AppCompatActivity = itemView.context as AppCompatActivity
    }
}