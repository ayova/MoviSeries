package com.ayova.moviseries.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayova.moviseries.R
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.ayova.moviseries.tmdb_models.*
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    val db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()
    var userPlaylist = UserPlaylist()
    var allPlaylists = arrayListOf<UserPlaylist>()

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
        getUserPlaylists()
        item_details_playlistadd.setOnClickListener{
            setupAddPlaylistBtn()
        }

    }

    private fun setupAddPlaylistBtn(){
        val builder = MaterialAlertDialogBuilder(activity!!)
        builder.setTitle("Añadir a:")
        allPlaylists.forEach {
            Log.v(TAG, it.listName!!)
        }

        val playlists: Array<String> = allPlaylists.map { it.listName }.toTypedArray() as Array<String>

        var checkedItem = 0
        builder.setSingleChoiceItems(playlists, checkedItem) { dialog, chosenId ->
            Log.v(TAG, "Chose: ${playlists[chosenId]}")
            checkedItem = chosenId
        }
        builder.setPositiveButton("Add") { dialog, which ->
            Log.v(TAG, "In the end chose: ${playlists[checkedItem]}")

//            userPlaylist.listName = allPlaylists[checkedItem].id

        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

//    fun addToPlaylist(id: String) {
//        userPlaylist.listName = taskName.text.toString()
//
//        db.collection("users").document(id).collection("tasks").add(newTask)
//            .addOnSuccessListener {
//                finish()
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error writing document", e)
//            }
//    }

    private fun getUserPlaylists() {
        db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .collection("playlists").get()
            .addOnSuccessListener { result ->
                allPlaylists.clear()
                allPlaylists.addAll(result.toObjects(UserPlaylist::class.java))
                for (document in result) {
                    Log.d(TAG, "Playlist with id: ${document.id} => ${document.data}")
                    val playlist = document.toObject(UserPlaylist::class.java)
                    playlist.id = document.id
                    Log.v("saving:", playlist.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun findByType(itemId: String, type: String) {
        when (type) {
            ItemDetailsType.movie.toString() -> findMovieById(itemId)
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
                Log.v(TAG, body?.overview.toString())
                if (response.isSuccessful && body != null) {
                    findTvShow = body
                    // Imprimir aqui el listado
                    Log.v(TAG, findTvShow.toString())

                    // Populate fragment
                    Picasso.get().load("${TmdbApi.POSTER_URL}${findTvShow?.poster_path}").into(item_details_image)
                    item_details_title.text = findTvShow?.name
                    item_details_description.text = findTvShow?.overview
                    var genres = ""
                    findTvShow?.genres?.forEach { genre ->
                        genres += "${genre.name} "
                    }
                    item_details_genres.text = genres
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
