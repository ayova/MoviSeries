package com.ayova.moviseries.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.ayova.moviseries.R
import com.ayova.moviseries.adapters.HomeRecyclerAdapter
import com.ayova.moviseries.models.Genre
import com.ayova.moviseries.models.Movie_Genres
import com.ayova.moviseries.omdb_api.TmdbApi
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val TAG = "miapp"
    val genres = ArrayList<Genre>()
    private var recyclerAdapter: HomeRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recycler
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        home_recyclerview.layoutManager = layoutManager
        recyclerAdapter = HomeRecyclerAdapter(genres)
        home_recyclerview.adapter = recyclerAdapter

        TmdbApi.initService()
        getGenres()
    }

    private fun getGenres() {
        val call = TmdbApi.service.getMovieGenres()
        call.enqueue(object : Callback<Movie_Genres> {
            override fun onResponse(call: Call<Movie_Genres>, response: Response<Movie_Genres>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.genres.toString())
                    genres.clear()
                    genres.addAll(body.genres)
                    // Imprimir aqui el listado
//                    body.genres.forEach{ genre ->
//                        Log.v(TAG, genre.name)
//                    }
                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<Movie_Genres>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }
}
