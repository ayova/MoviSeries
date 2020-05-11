package com.ayova.moviseries.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ayova.moviseries.R
import com.ayova.moviseries.adapters.HomeItemsRecyclerAdapter
import com.ayova.moviseries.adapters.HomeRecyclerAdapter
import com.ayova.moviseries.models.*
import com.ayova.moviseries.omdb_api.TmdbApi
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_recycler_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    val TAG = "miapp"
    val movieGenres = ArrayList<Genre>()
    val tvGenres = ArrayList<Genre>()
    val discoveredMovies = ArrayList<DiscoveredMovie>()
    val discoveredTV = ArrayList<DiscoveredTV>()
    val searchedMovies = ArrayList<SearchedMovie>()
    val searchedTVs = ArrayList<SearchedTV>()
    var findMovie: MovieDetails? = null
    var findTvShow: TVShowDetails? = null
    private var recyclerAdapter: HomeRecyclerAdapter? = null
    private var recyclerAdapter2: HomeItemsRecyclerAdapter? = null

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
        recyclerAdapter = HomeRecyclerAdapter(movieGenres, activity!!.applicationContext)
        home_recyclerview.adapter = recyclerAdapter

        // Recycler
//        val layoutManager2 = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
//        home_recycler_item_recycler.layoutManager = layoutManager2
//        recyclerAdapter2 = HomeItemsRecyclerAdapter(arrayListOf("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
//            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
//            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
//            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
//            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1",
//            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.humanesociety.org%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1240x698%2Fpublic%2F2018%2F06%2Fcat-217679.jpg%3Fh%3Dc4ed616d%26itok%3D3qHaqQ56&f=1&nofb=1"))
//        home_recycler_item_recycler.adapter = recyclerAdapter2

        //init connection
        TmdbApi.initService()

        //start requesting
        getMovieGenres()
        getTVGenres()
        discoverMovies()
        discoverTV()
        searchedMovie()
        searchedTV()
        findMovieById()
        findTvShowById()
    }







    /**
     * Functions underneath
     */

    private fun getMovieGenres() {
        val call = TmdbApi.service.getMovieGenres()
        call.enqueue(object : Callback<Movie_Genres> {
            override fun onResponse(call: Call<Movie_Genres>, response: Response<Movie_Genres>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    movieGenres.clear()
                    movieGenres.addAll(body.genres)
                    Log.v(TAG, movieGenres.toString())
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

    private fun getTVGenres() {
        val call = TmdbApi.service.getTVGenres()
        call.enqueue(object : Callback<TV_Genres> {
            override fun onResponse(call: Call<TV_Genres>, response: Response<TV_Genres>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    tvGenres.clear()
                    tvGenres.addAll(body.genres)
                    // Imprimir aqui el listado
                    Log.v(TAG, tvGenres.toString())
//                    body.genres.forEach{ genre ->
//                        Log.v(TAG, genre.name)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<TV_Genres>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun discoverMovies() {
        val call = TmdbApi.service.discoverMovies()
        call.enqueue(object : Callback<DiscoverMovies> {
            override fun onResponse(call: Call<DiscoverMovies>, response: Response<DiscoverMovies>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    discoveredMovies.clear()
                    discoveredMovies.addAll(body.results)
                    // Imprimir aqui el listado
                    Log.v(TAG, discoveredMovies.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<DiscoverMovies>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun discoverTV() {
        val call = TmdbApi.service.discoverTV()
        call.enqueue(object : Callback<DiscoverTV> {
            override fun onResponse(call: Call<DiscoverTV>, response: Response<DiscoverTV>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    discoveredTV.clear()
                    discoveredTV.addAll(body.results)
                    // Imprimir aqui el listado
                    Log.v(TAG, discoveredTV.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<DiscoverTV>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun searchedMovie() {
        val call = TmdbApi.service.searchMovie(query = "ghost")
        call.enqueue(object : Callback<SearchedMovies> {
            override fun onResponse(call: Call<SearchedMovies>, response: Response<SearchedMovies>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    searchedMovies.clear()
                    searchedMovies.addAll(body.results)
                    // Imprimir aqui el listado
                    Log.v(TAG, searchedMovies.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<SearchedMovies>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun searchedTV() {
        val call = TmdbApi.service.searchTV(query = "flash")
        call.enqueue(object : Callback<SearchedTVs> {
            override fun onResponse(call: Call<SearchedTVs>, response: Response<SearchedTVs>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    searchedTVs.clear()
                    searchedTVs.addAll(body.results)
                    // Imprimir aqui el listado
                    Log.v(TAG, searchedTVs.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<SearchedTVs>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun findMovieById() {
        val call = TmdbApi.service.findMovieById(movie_id = "419704")
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    findMovie = body
                    // Imprimir aqui el listado
                    Log.v(TAG, findMovie.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun findTvShowById() {
        val call = TmdbApi.service.findTVShowById(tv_id = "63247")
        call.enqueue(object : Callback<TVShowDetails> {
            override fun onResponse(call: Call<TVShowDetails>, response: Response<TVShowDetails>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    findTvShow = body
                    // Imprimir aqui el listado
                    Log.v(TAG, findTvShow.toString())
//                    discoveredMovies.forEach{ movie ->
//                        Log.v(TAG, movie.title)
//                    }
//                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<TVShowDetails>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }
    }
