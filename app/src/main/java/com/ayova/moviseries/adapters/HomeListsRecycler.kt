package com.ayova.moviseries.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.models.Genre
import com.ayova.moviseries.models.HomeList
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.home_recycler_item.view.*

class HomeListsRecycler(private val homeLists: ArrayList<HomeList>?, actContext: Context) : RecyclerView.Adapter<HomeListsRecycler.MainViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return MainViewHolder(v)
    }

    /**
     * @param recyclerView is the child recycler. Displays each item within the list
     */
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        // Get the list
        val data = homeLists?.get(position)
        // Set title
        holder.categoryTitle.text = data?.name
        // Prepare child recycler
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
//        childLayoutManager.initialPrefetchItemCount = 5
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = HomeItemsRecycler(data?.itemsList as ArrayList<Any>)
            setRecycledViewPool(viewPool)
        }
    }
    override fun getItemCount(): Int {
        return homeLists?.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val categoryTitle: MaterialTextView = v.findViewById(R.id.home_recycler_item_category)
        val recyclerView: RecyclerView = itemView.home_recycler_item_recycler

    }
}