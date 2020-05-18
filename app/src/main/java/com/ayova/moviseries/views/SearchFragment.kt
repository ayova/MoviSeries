package com.ayova.moviseries.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayova.moviseries.R
import com.ayova.moviseries.SignInActivity
import com.ayova.moviseries.adapters.HomeListsRecycler
import com.ayova.moviseries.adapters.SearchResultsRecyclerAdapter
import com.ayova.moviseries.models.SearchedMovie
import com.ayova.moviseries.models.SearchedMovies
import com.ayova.moviseries.models.SearchedTV
import com.ayova.moviseries.models.SearchedTVs
import com.ayova.moviseries.tmdb_api.TmdbApi
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private val TAG = "miapp"
    val searchedMovies = ArrayList<SearchedMovie>()
    val searchedTVs = ArrayList<SearchedTV>()
    var listOfItems: ArrayList<Any> = ArrayList()
    lateinit var recyclerAdapter: SearchResultsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_btn_opensearch.setOnClickListener {
            startActivity(Intent(activity!!.applicationContext,SignInActivity::class.java))
        }

        // Recycler
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        search_frag_recycler.layoutManager = layoutManager
        recyclerAdapter = SearchResultsRecyclerAdapter(listOfItems, activity!!.applicationContext)
        search_frag_recycler.adapter = recyclerAdapter

        (search_frag_searchview as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()){
                    Log.v(TAG, query)
                    searchedMovie(query)
//                    searchedTV(query)
                    listOfItems.clear()
                    listOfItems.addAll(searchedTVs)
                    listOfItems.addAll(searchedMovies)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    /**
     * Functions for searching movie/tv show with string provided
     */
    private fun searchedMovie(query: String) {
        val call = TmdbApi.service.searchMovie(query = query)
        call.enqueue(object : Callback<SearchedMovies> {
            override fun onResponse(call: Call<SearchedMovies>, response: Response<SearchedMovies>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    searchedMovies.clear()
                    searchedMovies.addAll(body.results)
                    listOfItems.clear()
                    listOfItems.addAll(body.results)
                    searchedTV(query)
                    // Imprimir aqui el listado
                    Log.v(TAG, searchedMovies.toString())
                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<SearchedMovies>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun searchedTV(query: String) {
        val call = TmdbApi.service.searchTV(query = query)
        call.enqueue(object : Callback<SearchedTVs> {
            override fun onResponse(call: Call<SearchedTVs>, response: Response<SearchedTVs>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    searchedTVs.clear()
                    searchedTVs.addAll(body.results)
                    listOfItems.addAll(body.results)
                    // Imprimir aqui el listado
                    Log.v(TAG, searchedTVs.toString())
                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<SearchedTVs>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }
}

