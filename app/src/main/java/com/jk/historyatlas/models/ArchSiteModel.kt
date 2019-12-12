package com.jk.historyatlas.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ArchSiteModel(var id: Long = 0,
                         var title: String = "",
                         var desc: String = "",
                         var image: String = "",
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var visited: Boolean = false): Parcelable
