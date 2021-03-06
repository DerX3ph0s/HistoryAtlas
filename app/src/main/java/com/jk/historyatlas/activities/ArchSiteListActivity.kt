package com.jk.historyatlas.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jk.historyatlas.R
import com.jk.historyatlas.adapters.ArchSiteAdapter
import com.jk.historyatlas.adapters.ArchSiteListener
import com.jk.historyatlas.main.MainApp
import com.jk.historyatlas.models.ArchSiteModel
import kotlinx.android.synthetic.main.activity_arch_site_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class ArchSiteListActivity : AppCompatActivity(), ArchSiteListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_arch_site_list)
        app = application as MainApp
        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ArchSiteAdapter(app.archsites.findAll(), this)
        loadArchSites()
    }

    private fun loadArchSites() {
        showArchSites( app.archsites.findAll())
    }

    fun showArchSites (archsites: List<ArchSiteModel>) {
        recyclerView.adapter = ArchSiteAdapter(archsites, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<ArchSiteActivity>(0)
            R.id.item_logout -> doLogout()
            R.id.item_settings -> startActivityForResult<SettingsActivity>(1)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onArchSiteClick(archsite: ArchSiteModel) {
        startActivityForResult(intentFor<ArchSiteActivity>().putExtra("archsite_edit",archsite),0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadArchSites()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun doLogout(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}