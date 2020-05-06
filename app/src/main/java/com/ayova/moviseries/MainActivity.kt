package com.ayova.moviseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import android.widget.ImageView
import com.ayova.moviseries.models.Movie_Genres
import com.ayova.moviseries.views.AccountFragment
import com.ayova.moviseries.views.HomeFragment
import com.ayova.moviseries.views.MyListsFragment
import com.ayova.moviseries.views.SearchFragment
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
    private var genresList = ArrayList<Movie_Genres>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startLogoAnimation()
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                /** Home fragment */
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,HomeFragment()).commit()
                    true
                }
                /** My lists fragment */
                R.id.action_myLists -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,MyListsFragment()).commit()
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
                    //TODO might need to add an exception in here
                    false
                }
            }
        }


//        OmdbApi.initService()
//        getGenres()
    }

    /**
     * Logo animation on start
     */
    private fun startLogoAnimation(){
        val logoAnim: ImageView = findViewById(R.id.main_logo_for_anim)
        val render = Render(this)

        render.setAnimation(Fade().In(logoAnim).setDuration(1000))
        render.start()
        Handler().postDelayed({
            render.setAnimation(Attention().Flash(logoAnim).setDuration(900))
            render.start()
            Handler().postDelayed({
                render.setAnimation(Fade().Out(logoAnim).setDuration(1600))
                render.start()
                Handler().postDelayed({
                    // hide imageView
                    logoAnim.visibility = FrameLayout.GONE
                    // reveal bottom nav menu
                    main_bottom_navigation.visibility = FrameLayout.VISIBLE
                    // reveal (previously gone) frame container
                    main_frame_container.visibility = FrameLayout.VISIBLE
                    // go to home fragment
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_container,HomeFragment()).commit()
                },1600)
            },1000)
        },1500)
    }
}


