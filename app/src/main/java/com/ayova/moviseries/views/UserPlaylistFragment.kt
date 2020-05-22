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
    var tappedPlaylist: UserPlaylist? = null

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
        Log.v("miapp", playlistId)
        adapter = UserPlaylistAdapter(tappedPlaylist)
        user_playlist_recycler.setHasFixedSize(true)
        user_playlist_recycler.layoutManager = LinearLayoutManager(activity!!)
        user_playlist_recycler.adapter = adapter
        getPlaylist(playlistId)
        adapter?.notifyDataSetChanged()
    }

    private fun getPlaylist(playlist: String): UserPlaylist {
        var tappedPlaylist = UserPlaylist()
        playlistsRef.document(playlist).get()
            .addOnSuccessListener { snapshot ->
                tappedPlaylist = snapshot.toObject(UserPlaylist::class.java)!!

                adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Couldn't get items in playlist", "<-- ERROR", e ) }
        adapter?.notifyDataSetChanged()
        return tappedPlaylist
    }

    private fun setupRecyclerView(playlist: UserPlaylist) {
        adapter = UserPlaylistAdapter(playlist)
        user_playlist_recycler.setHasFixedSize(true)
        user_playlist_recycler.layoutManager = GridLayoutManager(activity!!, 3)
        user_playlist_recycler.adapter = adapter
    }

}
