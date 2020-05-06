package com.ayova.moviseries.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.models.Genre
import com.google.android.material.textview.MaterialTextView

class HomeRecyclerAdapter(private val movies: ArrayList<Genre>?) : RecyclerView.Adapter<HomeRecyclerAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return MainViewHolder(v)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = movies?.get(position)
        data?.let {
            holder.bindItems(it)
        }
    }
    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val categoryTitle = v.findViewById<MaterialTextView>(R.id.home_recycler_item_category)
        fun bindItems(genre: Genre) {
            categoryTitle.text = genre.name
        }
    }
}