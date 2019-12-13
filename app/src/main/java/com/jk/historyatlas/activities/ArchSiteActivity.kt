package com.jk.historyatlas.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jk.historyatlas.R
import kotlinx.android.synthetic.main.activity_arch_site.*

class ArchSiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch_site)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
    }
}
