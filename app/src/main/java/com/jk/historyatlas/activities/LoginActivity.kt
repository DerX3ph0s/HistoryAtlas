package com.jk.historyatlas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.firebase.auth.FirebaseAuth
import com.jk.historyatlas.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

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

        progressBar.visibility = GONE
    }

    fun doSignUp (email: String, password: String) {
        showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, ArchSiteListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
            }
            hideProgress()
        }
    }

    fun doLogin (email: String, password: String) {
        showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, ArchSiteListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
            }
            hideProgress()
        }
    }

    fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = GONE
    }
}
