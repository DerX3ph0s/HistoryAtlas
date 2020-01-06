package com.jk.historyatlas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jk.historyatlas.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbarLogin.title = getString(R.string.title_activity_login)

        signUp.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                doSignUp(email,password)
            }
        }

        logIn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                doLogin(email,password)
            }
        }
    }

    fun doSignUp (email: String, password: String) {
        val intent = Intent(this, ArchSiteListActivity::class.java)
        startActivity(intent)
    }

    fun doLogin (email: String, password: String) {
        val intent = Intent(this, ArchSiteListActivity::class.java)
        startActivity(intent)
    }
}
