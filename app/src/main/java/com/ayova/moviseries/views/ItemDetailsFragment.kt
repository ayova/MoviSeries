package com.ayova.moviseries.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.ayova.moviseries.R
import com.ayova.moviseries.firebase_models.User
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.ayova.moviseries.tmdb_models.*
import com.ayova.moviseries.tmdb_api.TmdbApi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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
    var allPlaylists = arrayListOf<String>()
    var movieImage: String? = null
    var tvImage: String? = null

    companion object{
        private const val ITEM_ID = "itemDetailsID"
        private const val ITEM_TYPE = "itemDetailsType"

        fun newInstance(id: String, type: String):ItemDetailsFragment{
            val args = Bundle()
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
            setupAddPlaylistBtn(itemId, itemType)
        }
    }

    private fun setupAddPlaylistBtn(item: String, type: String){
        val builder = MaterialAlertDialogBuilder(activity!!)
        val inflater = layoutInflater
        builder.setTitle("Add to:")

        val dialogLayout = inflater.inflate(R.layout.alert_dialog_add_playlist, null)
        val editTextName = dialogLayout.findViewById<TextInputEditText>(R.id.alert_add_playlist_name)

        // array for setting singleChoiceItems
        val playlists: Array<String> = allPlaylists.map { it }.toTypedArray() as Array<String>
        var checkedItem = 0
        builder.setSingleChoiceItems(playlists, checkedItem) { dialog, chosenId ->
            Log.v(TAG, "Chose: ${playlists[chosenId]}")
            checkedItem = chosenId
        }
        builder.setPositiveButton("Add") { dialog, which ->
            if (editTextName.text.toString().isBlank()) {
                Log.v(TAG, "In the end chose: ${playlists[checkedItem]}")
                if (type == ItemDetailsType.movie.toString()) {
                    addToPlaylist(item, type, movieImage!!, playlists[checkedItem])
                }
                if (type == ItemDetailsType.tv_show.toString()) {
                    addToPlaylist(item, type, tvImage!!, playlists[checkedItem])
                }
            } else if (editTextName.text.toString().isNotEmpty()) {
                Log.v(TAG, "In the end chose: ${editTextName.text.toString()}")
                if (!playlists.contains(editTextName.text.toString())) {
                    if (type == ItemDetailsType.movie.toString()) {
                        createUserPlaylist(item, type, editTextName.text.toString())
                    }
                    if (type == ItemDetailsType.tv_show.toString()) {
                        createUserPlaylist(item, type, editTextName.text.toString())
                    }
                }
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.setView(dialogLayout)
        builder.show()
    }

    private fun addToPlaylist(itemId: String, itemType: String, image: String, playlistName: String) {
        Log.e(TAG, "got to addToPlaylist()")
        val playlistsRef = db.collection("users").document(auth.currentUser!!.uid).collection("playlists")

        playlistsRef.get().addOnSuccessListener { playlists ->
            for (playlist in playlists) {
                if (playlist["listName"] == playlistName) {
                    if (itemType == ItemDetailsType.movie.toString()) {
                        playlistsRef.document(playlist.id).update(
                            "moviesAdded",
                            FieldValue.arrayUnion(mapOf("id" to itemId, "image" to image))
                        )
                    } else if (itemType == ItemDetailsType.tv_show.toString()) {
                        playlistsRef.document(playlist.id).update(
                            "tvShowsAdded",
                            FieldValue.arrayUnion(mapOf("id" to itemId, "image" to image))
                        )
                    }
                }
            }
        }
    }

    private fun createUserPlaylist(itemId: String, itemType: String, playlistName: String) {
        val playlistToBeAdded = UserPlaylist()
        playlistToBeAdded.listName = playlistName
        db.collection("users").document(auth.currentUser!!.uid).collection("playlists").add(playlistToBeAdded)
            .addOnSuccessListener {
                Log.v(TAG, "Playlist added with id: ${it.id}")
                if (itemType == ItemDetailsType.movie.toString()) {
                    addToPlaylist(itemId, itemType, movieImage!!, playlistName)
                }
                if (itemType == ItemDetailsType.tv_show.toString()) {
                    addToPlaylist(itemId, itemType, tvImage!!, playlistName)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            }

    }

    private fun getUserPlaylists() {
        db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .collection("playlists").get().addOnSuccessListener { querySnapshot ->
                allPlaylists.clear()
                for (document in querySnapshot) {
                    Log.v(TAG,document["listName"].toString())
                    allPlaylists.add(document["listName"].toString())
                }
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

                    // Populate fragment
                    Picasso.get().load("${TmdbApi.POSTER_URL}${findMovie?.poster_path}").into(item_details_image)
                    movieImage = "${TmdbApi.POSTER_URL}${findMovie?.poster_path}"
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

                    // Populate fragment
                    Picasso.get().load("${TmdbApi.POSTER_URL}${findTvShow?.poster_path}").into(item_details_image)
                    tvImage = "${TmdbApi.POSTER_URL}${findTvShow?.poster_path}"
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
