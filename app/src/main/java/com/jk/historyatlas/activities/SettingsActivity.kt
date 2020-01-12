package com.jk.historyatlas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.jk.historyatlas.R
import com.jk.historyatlas.main.MainApp
import com.jk.historyatlas.models.ArchSiteModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    lateinit var app: MainApp
    var archsites: List<ArchSiteModel> = listOf()
    var auth = FirebaseAuth.getInstance()
    var user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        app = application as MainApp

        toolbarSettings.title = getString(R.string.title_activity_settings)
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        archsites = app.archsites.findAll()


        textViewUserEmail.setText(app.userEmail)
        textViewNumberAllSites.setText(archsites.size.toString())
        textViewNumberVisitedSites.setText(getNumberVisited().size.toString())

        buttonChangePW.setOnClickListener {
            var pw = editTextPassword.text.toString()

            user?.updatePassword(pw)
        }
    }

    fun getNumberVisited(): List<ArchSiteModel>{
        return archsites.filter {it.visited == true}
    }

}
