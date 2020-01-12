package com.jk.historyatlas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ArchSiteModel(var id: Long = 0,
                         var email: String = "",
                         var title: String = "",
                         var desc: String = "",
                         var image: String = "",
                         var location: Location = Location(),
                         var dateVisited: Calendar? = null,
                         var visited: Boolean = false,
                         var stars: Float = 0.0f,
                         var notes: String = ""): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable