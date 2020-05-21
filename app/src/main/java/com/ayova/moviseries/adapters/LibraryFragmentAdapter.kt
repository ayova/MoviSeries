package com.ayova.moviseries.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.textview.MaterialTextView

class LibraryFragmentAdapter(playlist: FirestoreRecyclerOptions<UserPlaylist>) :
    FirestoreRecyclerAdapter<UserPlaylist, LibraryFragmentAdapter.MainViewHolder>(playlist) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.library_playlists_item, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int, model: UserPlaylist) {
        holder.playlistTitle.text = model.listName
    }

    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val playlistTitle: MaterialTextView = itemView.findViewById(R.id.library_playlist_item_title)
    }
}
