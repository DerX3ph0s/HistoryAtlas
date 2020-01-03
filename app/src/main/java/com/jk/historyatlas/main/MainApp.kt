package com.jk.historyatlas.main

import android.app.Application
import com.jk.historyatlas.models.ArchSiteStore
import com.jk.historyatlas.models.json.ArchSiteJSONStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var archsites: ArchSiteStore

    override fun onCreate() {
        super.onCreate()

        archsites = ArchSiteJSONStore(applicationContext)

        info("HistoryAtlas started")
    }
}