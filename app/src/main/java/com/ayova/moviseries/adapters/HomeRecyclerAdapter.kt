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
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.home_recycler_item.*
import kotlinx.android.synthetic.main.home_recycler_item.view.*

class HomeRecyclerAdapter(private val genres: ArrayList<Genre>?, actContext: Context) : RecyclerView.Adapter<HomeRecyclerAdapter.MainViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return MainViewHolder(v)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = genres?.get(position)
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 4
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = HomeItemsRecyclerAdapter(arrayListOf("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1"))
            setRecycledViewPool(viewPool)
        }
        data?.let {
            holder.bindItems(it)
            holder.itemView.setOnClickListener{
                Log.v("miapp", data.name)
            }
        }
    }
    override fun getItemCount(): Int {
        return genres?.size ?: 0
    }
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val categoryTitle = v.findViewById<MaterialTextView>(R.id.home_recycler_item_category)
        fun bindItems(genre: Genre) {
            categoryTitle.text = genre.name
        }
        val recyclerView: RecyclerView = itemView.home_recycler_item_recycler

    }
}