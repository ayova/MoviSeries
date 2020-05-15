package com.ayova.moviseries.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayova.moviseries.R
import com.ayova.moviseries.models.ItemOverview

/**
 * A simple [Fragment] subclass.
 */
class ItemDetails : Fragment() {

    companion object{
        private const val ITEM_ID = "itemDetailsID"
        private const val ITEM_TYPE = "itemDetailsType"

        fun newInstance(id: String, type: String):ItemDetails{
            val args: Bundle = Bundle()
            args.putString(ITEM_ID, id)
            args.putString(ITEM_TYPE, type)
            val fragment = ItemDetails()
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
        Log.v("miapp", bundle?.getString(ITEM_ID).toString())
        Log.v("miapp", bundle?.getString(ITEM_TYPE).toString())
    }
}
