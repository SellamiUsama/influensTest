package com.example.influensapp.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.example.influensapp.R
import com.example.influensapp.Views.Authentification.LoginActivity
import com.example.influensapp.Views.PopularShows.PopularShowsActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Glide.with(this)
            .load(R.drawable.cinema)
            .into(im_splashscreen)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Handler().postDelayed({

            if(currentUser == null)
                startActivity(Intent(this,LoginActivity::class.java))
            else
                startActivity(Intent(this,PopularShowsActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }

}
