package com.ayova.moviseries.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.ayova.moviseries.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(custom_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menusearch, menu)
        val searchItem = menu?.findItem(R.id.toolbar_action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.v("miapp", "Cuando pulso el boton de envio del teclado")
                // TODO: IMPLEMENT API SEARCH HERE!!!
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                Log.v("miapp", "Cuando escribo")
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
