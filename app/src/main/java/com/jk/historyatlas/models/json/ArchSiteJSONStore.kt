package com.jk.historyatlas.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jk.historyatlas.models.ArchSiteModel
import com.jk.historyatlas.models.ArchSiteStore
import org.jetbrains.anko.AnkoLogger
import com.jk.historyatlas.helpers.exists
import com.jk.historyatlas.helpers.read
import com.jk.historyatlas.helpers.write
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "archsite.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<ArchSiteModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ArchSiteJSONStore : ArchSiteStore, AnkoLogger {

    val context: Context
    var archsites = mutableListOf<ArchSiteModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ArchSiteModel> {
        return archsites
    }

    override fun create(archsite: ArchSiteModel) {
        archsite.id = generateRandomId()
        archsites.add(archsite)
        serialize()
    }


    override fun update(archsite: ArchSiteModel) {
        val archsitesList = findAll() as ArrayList<ArchSiteModel>
        var foundarchsite: ArchSiteModel? = archsitesList.find { p -> p.id == archsite.id }
        if (foundarchsite != null) {
            foundarchsite.title = archsite.title
            foundarchsite.desc = archsite.desc
            foundarchsite.image = archsite.image
            foundarchsite.location = archsite.location
            foundarchsite.visited = archsite.visited
            foundarchsite.notes = archsite.notes
            foundarchsite.dateVisited = archsite.dateVisited

        }
        serialize()
    }

    override fun delete(archsite: ArchSiteModel) {
        archsites.remove(archsite)
        serialize()
    }

    override fun findById(id:Long) : ArchSiteModel? {
        val foundarchsite: ArchSiteModel? = archsites.find { it.id == id }
        return foundarchsite
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(archsites,
            listType
        )
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        archsites = Gson().fromJson(jsonString, listType)
    }

    override fun clear() {
        archsites.clear()
    }
}