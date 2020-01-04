package com.jk.historyatlas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ArchSiteModel(var id: Long = 0,
                         var title: String = "",
                         var desc: String = "",
                         var image: List<String> = listOf(),
                         var location: Location = Location(),
                         var dateVisited: Calendar? = null,
                         var visited: Boolean = false,
                         var notes: String = ""): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable