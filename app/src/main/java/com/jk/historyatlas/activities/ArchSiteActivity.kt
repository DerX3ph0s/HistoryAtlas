package com.jk.historyatlas.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jk.historyatlas.R
import com.jk.historyatlas.helpers.readImage
import com.jk.historyatlas.helpers.readImageFromPath
import com.jk.historyatlas.helpers.showImagePicker
import com.jk.historyatlas.main.MainApp
import com.jk.historyatlas.models.ArchSiteModel
import com.jk.historyatlas.models.Location
import kotlinx.android.synthetic.main.activity_arch_site.*

class ArchSiteActivity : AppCompatActivity() {

    var archsite = ArchSiteModel()
    lateinit var app:MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch_site)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("archsite_edit")) {
            edit = true
            archsite = intent.extras?.getParcelable<ArchSiteModel>("archsite_edit")!!
            archsiteTitle.setText(archsite.title)
            archsiteDescription.setText(archsite.desc)
            //archsiteImage.setImageBitmap(readImageFromPath(this, archsite.image))
            if (archsite.image != null) {
                chooseImage.setText(R.string.change_archsite_image)
            }
            this.showLocation(archsite.location)
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_archsite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.archsites.delete(archsite)
                finish()
            }
            R.id.item_save -> {
                archsite.title = archsiteTitle.text.toString()
                archsite.desc = archsiteDescription.text.toString()
                archsite.notes = archsiteAdditionalNotes.text.toString()
                //archsite.dateVisited = archsiteDateVisited.getDate()
                if (edit) {
                    app.archsites.update(archsite)
                } else {
                    app.archsites.create(archsite)
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLocation(location: Location) {
        textViewLat.setText("%.6f".format(location.lat))
        textViewLng.setText("%.6f".format(location.lng))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    //archsite.image = data.getData().toString()
                    archsiteImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_archsite_image)
                }
            }

        }
    }

}
