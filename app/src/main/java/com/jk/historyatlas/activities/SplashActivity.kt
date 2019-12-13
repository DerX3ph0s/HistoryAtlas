package com.jk.historyatlas.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jk.historyatlas.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen(){
        Handler().postDelayed({
            //change to ArchSiteListActivity
            val intent = Intent(this, ArchSiteActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
