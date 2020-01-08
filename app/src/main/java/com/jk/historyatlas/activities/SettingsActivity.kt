package com.jk.historyatlas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jk.historyatlas.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbarSettings.title = getString(R.string.title_activity_settings)
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
