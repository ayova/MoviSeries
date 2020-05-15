package com.ayova.moviseries.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayova.moviseries.R
import com.ayova.moviseries.models.*
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ItemDetailsFragment : Fragment() {

    private var findMovie: MovieDetails? = null
    private var findTvShow: TVShowDetails? = null
    private val TAG = "miappdets"

    companion object{
        private const val ITEM_ID = "itemDetailsID"
        private const val ITEM_TYPE = "itemDetailsType"

        fun newInstance(id: String, type: String):ItemDetailsFragment{
            val args: Bundle = Bundle()
            args.putString(ITEM_ID, id)
            args.putString(ITEM_TYPE, type)
            val fragment = ItemDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val itemId = bundle?.getString(ITEM_ID).toString()
        val itemType = bundle?.getString(ITEM_TYPE).toString()

        findByType(itemId, itemType)

    }

    private fun findByType(itemId: String, type: String) {
        when (type) {
            ItemDetailsType.movie.toString() -> {
                findMovieById(itemId)
            }
            ItemDetailsType.tv_show.toString() -> findTvShowById(itemId)
        }
    }

    private fun findMovieById(movieId: String) {
        val call = TmdbApi.service.findMovieById(movie_id = movieId)
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    findMovie = body
                    // Imprimir aqui el listado
                    // Log.v(TAG, findMovie.toString())

                    // Populate fragment
                    Picasso.get().load("${TmdbApi.POSTER_URL}${findMovie?.poster_path}").into(item_details_image)
                    item_details_title.text = findMovie?.title
                    item_details_description.text = findMovie?.overview
                    var genres = ""
                    findMovie?.genres?.forEach { genre ->
                        genres += "${genre.name} "
                    }
                    item_details_genres.text = genres

                } else {
                    Log.e(TAG, response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    private fun findTvShowById(tvId: String) {
        val call = TmdbApi.service.findTVShowById(tv_id = tvId)
        call.enqueue(object : Callback<TVShowDetails> {
            override fun onResponse(call: Call<TVShowDetails>, response: Response<TVShowDetails>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    findTvShow = body
                    // Imprimir aqui el listado
                    Log.v(TAG, findTvShow.toString())
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
