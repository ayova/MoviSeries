package com.ayova.moviseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash.*
import render.animations.Attention
import render.animations.Fade
import render.animations.Render

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startLogoAnimation()
    }

    /**
     * Logo animation on start
     */
    private fun startLogoAnimation(){
        val render = Render(this)
        render.setAnimation(Fade().In(main_logo_for_anim).setDuration(400))
        render.start()
        Handler().postDelayed({
            render.setAnimation(Attention().Flash(main_logo_for_anim).setDuration(500))
            render.start()
            Handler().postDelayed({
                // go to main activity
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }, 1000)
        },700)
    }
}
