package com.jk.historyatlas.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jk.historyatlas.R
import com.jk.historyatlas.helpers.*
import com.jk.historyatlas.main.MainApp
import com.jk.historyatlas.models.ArchSiteModel
import com.jk.historyatlas.models.Location
import kotlinx.android.synthetic.main.activity_arch_site.*
import org.jetbrains.anko.toast
import java.util.*

class ArchSiteActivity : AppCompatActivity() {

    var archsite = ArchSiteModel()
    lateinit var app: MainApp
    lateinit var map: GoogleMap
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var edit = false

    lateinit var locationService: FusedLocationProviderClient
    val locationRequest = createDefaultLocationRequest()
    var defaultLocation = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch_site)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        locationService = LocationServices.getFusedLocationProviderClient(this)

        app = application as MainApp

        if (intent.hasExtra("archsite_edit")) {
            edit = true
            archsite = intent.extras?.getParcelable<ArchSiteModel>("archsite_edit")!!
            archsiteTitle.setText(archsite.title)
            archsiteDescription.setText(archsite.desc)
            if (archsite.visited) {
                checkBox.setChecked(true)
            }
            //archsiteImage.setImageBitmap(readImageFromPath(this, archsite.image))
            if (archsite.image != null) {
                chooseImage.setText(R.string.change_archsite_image)
            }
            this.showLocation(archsite.location)
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            archsite.dateVisited?.set(Calendar.YEAR, year)
            archsite.dateVisited?.set(Calendar.MONTH, month)
            archsite.dateVisited?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        archsiteDateVisited.setOnClickListener {
            val dpd = DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        checkBox.setOnClickListener( View.OnClickListener {
            archsite.visited = checkBox.isChecked
        })

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            doConfigureMap(it)
            it.setOnMapClickListener { doSetLocation() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_archsite, menu)
        if(edit){
            menu?.findItem(R.id.item_delete)?.isVisible = true
        }
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
                if (archsite.title.isEmpty()) {
                    toast("Title is empty")
                } else{
                    if (edit) {
                        app.archsites.update(archsite.copy())
                    } else {
                        app.archsites.create(archsite.copy())
                        toast("New archsite created")
                    }
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
            LOCATION_REQUEST -> {
                val location = data?.extras?.getParcelable<Location>("location")!!
                archsite.location = location
                locationUpdate(location)
            }

        }
    }

    // Maps

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun doSetLocation() {
        intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("location", Location(archsite.location.lat, archsite.location.lng, archsite.location.zoom))
        startActivityForResult(intent, LOCATION_REQUEST)
    }


    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(archsite.location)
    }

    fun locationUpdate(location: Location) {
        archsite.location = location
        archsite.location.zoom = 15f
        map?.clear()
        val options = MarkerOptions().title(archsite.title).position(LatLng(archsite.location.lat, archsite.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(archsite.location.lat, archsite.location.lng), archsite.location.zoom))
        showLocation(archsite.location)
    }
}
