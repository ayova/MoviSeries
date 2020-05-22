package com.ayova.moviseries.views

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.ayova.moviseries.R
import com.ayova.moviseries.adapters.LibraryFragmentAdapter
import com.ayova.moviseries.adapters.UserPlaylistAdapter
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_user_playlist.*

/**
 * A simple [Fragment] subclass.
 */
class UserPlaylistFragment : Fragment() {

    private var adapter: UserPlaylistAdapter? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val playlistsRef = db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
    private var tappedPlaylist: UserPlaylist? = null

    companion object{
        private const val PLAYLIST_ID = "playlistID"

        fun newInstance(id: String):UserPlaylistFragment{
            val args = Bundle()
            args.putString(PLAYLIST_ID, id)
            val fragment = UserPlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val playlistId = bundle?.getString(PLAYLIST_ID).toString()
//        adapter = UserPlaylistAdapter(tappedPlaylist)
//        user_playlist_recycler.layoutManager = GridLayoutManager(activity!!, 2)
//        user_playlist_recycler.setHasFixedSize(true)
//        user_playlist_recycler.adapter = adapter
//        setupRecyclerView()
        getPlaylist(playlistId)
    }

    private fun getPlaylist(playlist: String) {
        var mPlaylist: UserPlaylist = UserPlaylist()
        playlistsRef.document(playlist).get()
            .addOnSuccessListener { plist ->
                Log.v("miapp", plist.toObject(UserPlaylist::class.java)?.moviesAdded.toString())
                mPlaylist.listName = plist.toObject(UserPlaylist::class.java)?.listName.toString()
                mPlaylist.moviesAdded = plist.toObject(UserPlaylist::class.java)?.moviesAdded
                mPlaylist.tvShowsAdded = plist.toObject(UserPlaylist::class.java)?.tvShowsAdded
                setupRecyclerView(mPlaylist)
            }
            .addOnFailureListener { e ->
                Log.e("Couldn't get items in playlist", "<-- ERROR", e ) }
    }

    private fun setupRecyclerView(playlist: UserPlaylist) {
        adapter = UserPlaylistAdapter(playlist)
        user_playlist_recycler.setHasFixedSize(true)
        user_playlist_recycler.layoutManager = GridLayoutManager(activity!!, 2)
        user_playlist_recycler.adapter = adapter
    }

}
