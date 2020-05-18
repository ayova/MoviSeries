package com.ayova.moviseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash.*
import render.animations.Attention
import render.animations.Fade
import render.animations.Render

class SplashActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var userSignedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // if user logged in: go to home fragment. else, go to sign in activity

        // init firebase auth
        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // user signed in
            userSignedIn = true
        }

        // pass the user. by default will be false (aka. not signed in)
        startLogoAnimation(userSignedIn)
    }

    /**
     * Logo animation on start
     */
    private fun startLogoAnimation(userSignedIn: Boolean){
        val render = Render(this)
        render.setAnimation(Fade().In(main_logo_for_anim).setDuration(400))
        render.start()
        Handler().postDelayed({
            render.setAnimation(Attention().Flash(main_logo_for_anim).setDuration(500))
            render.start()
            Handler().postDelayed({
                // go to main activity
                if (userSignedIn){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, SignInActivity::class.java))
                }
                this.finish()
            }, 1000)
        },700)
    }
}
