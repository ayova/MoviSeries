package com.ayova.moviseries.views

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

import com.ayova.moviseries.R
import kotlinx.android.synthetic.main.fragment_library.*

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    private var isLibraryEmpty: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)


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

}
