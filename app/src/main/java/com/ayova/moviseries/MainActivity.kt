package com.ayova.moviseries

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import android.widget.ImageView
import com.ayova.moviseries.models.Movie_Genres
import com.ayova.moviseries.views.*
import kotlinx.android.synthetic.main.activity_main.*
import render.animations.Attention
import render.animations.Fade
import render.animations.Render
import java.lang.Error

/**
 * This activity displays the app's logo for a couple
 * seconds and then loads home fragment (where it all starts)
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "miapp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,HomeFragment()).commit()

    }

    /**
     * Initial setup of the bottom navigation for the entire app
     */
    private fun setupBottomNavigation(){
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                /** Home fragment */
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,HomeFragment()).commit()
                    true
                }
                /** My lists fragment */
                R.id.action_myLists -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,MyListsFragment()).commit()
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container, ItemDetails.newInstance("asd","asdefg")).commit()

                    true
                }
                /** Search fragment */
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,SearchFragment()).commit()
                    true
                }
                /** Account fragment */
                R.id.action_account -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,AccountFragment()).commit()
                    true
                }
                else -> {
                    //TODO exception handling may be needed
                    false
                }
            }
        }
    }
}


