package com.ayova.moviseries.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.ayova.moviseries.R
import com.ayova.moviseries.adapters.HomeListsRecycler
import com.ayova.moviseries.interfaces.HomeItemClicked
import com.ayova.moviseries.models.*
import com.ayova.moviseries.tmdb_api.TmdbApi
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeItemClicked {

    val TAG = "miapp"
    val movieGenres = ArrayList<Genre>()
    val tvGenres = ArrayList<Genre>()
    val discoveredMovies = ArrayList<DiscoveredMovie>()
    val discoveredTV = ArrayList<DiscoveredTV>()
    val searchedMovies = ArrayList<SearchedMovie>()
    val searchedTVs = ArrayList<SearchedTV>()
    var findMovie: MovieDetails? = null
    var findTvShow: TVShowDetails? = null
    private var recyclerAdapter: HomeListsRecycler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // List of lists the recycler will display
        var listForRecycler = ArrayList<HomeList>()
        listForRecycler.add(HomeList("Popular movies", discoveredMovies))
        listForRecycler.add(HomeList("Popular TV shows", discoveredTV))

        // Recycler
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        home_recyclerview.layoutManager = layoutManager
        recyclerAdapter = HomeListsRecycler(listForRecycler, activity!!.applicationContext)
        home_recyclerview.adapter = recyclerAdapter

        //init connection
        TmdbApi.initService()

        //start requesting
        discoverMoviesAndSeries()
//        getMovieGenres()
//        getTVGenres()
//        discoverMovies()
//        discoverTV()
//        searchedMovie()
//        searchedTV()
//        findMovieById()
//        findTvShowById()
    }

    /**
     * Interface for registering movie/tv show clicked on
     */
    override fun onHomeItemClick(context: Context, position: Int) {
        Log.v(TAG, "Item clicked in position: $position")
    }

    /**
     * API functions underneath
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
//                    recyclerAdapter?.notifyDataSetChanged()
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

    private fun discoverMoviesAndSeries(){
        val call = TmdbApi.service.discoverMovies()
        call.enqueue(object : Callback<DiscoverMovies> {
            override fun onResponse(call: Call<DiscoverMovies>, response: Response<DiscoverMovies>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    discoveredMovies.clear()
                    discoveredMovies.addAll(body.results)
                    // Then, get TV shows
                    discoverTV()
                    // Finally update recycler info
                    recyclerAdapter?.notifyDataSetChanged()
                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<DiscoverMovies>, t: Throwable) {
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
                    recyclerAdapter?.notifyDataSetChanged()
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
