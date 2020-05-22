package com.ayova.moviseries.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayova.moviseries.R
import com.ayova.moviseries.adapters.LibraryFragmentAdapter
import com.ayova.moviseries.firebase_models.User
import com.ayova.moviseries.firebase_models.UserPlaylist
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_library.*


/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    private var isLibraryEmpty: Boolean = true
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val playlistsRef = db.collection("users").document(auth.currentUser!!.uid).collection("playlists")
    private var adapter: LibraryFragmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLibraryEmpty = false
        checkLibraryEmpty()
        setupRecyclerView()
    }

    /**
     * Function used to display either:
     * a library with user lists,
     * or the lack thereof
     */
    private fun checkLibraryEmpty(){
        if (isLibraryEmpty){
            library_lists_recycler.visibility = RelativeLayout.GONE
            library_relative_nolibraries.visibility = RelativeLayout.VISIBLE
        } else if (!isLibraryEmpty) {
            library_lists_recycler.visibility = RelativeLayout.VISIBLE
            library_relative_nolibraries.visibility = RelativeLayout.GONE
        }
    }

    fun setupRecyclerView() {
        var query: Query = playlistsRef.orderBy("listName")
        var playlists = FirestoreRecyclerOptions.Builder<UserPlaylist>()
            .setQuery(query, UserPlaylist::class.java)
            .build()
        adapter = LibraryFragmentAdapter(playlists)
        library_lists_recycler.setHasFixedSize(true)
        library_lists_recycler.layoutManager = LinearLayoutManager(activity!!)
        library_lists_recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop();
        adapter?.stopListening();
    }

}
