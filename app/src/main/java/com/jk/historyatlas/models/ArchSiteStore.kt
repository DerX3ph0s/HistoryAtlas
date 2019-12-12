package com.jk.historyatlas.models

interface ArchSiteStore {
    fun findAll(): List<ArchSiteModel>
    fun create(archsite: ArchSiteModel)
    fun update(archsite: ArchSiteModel)
    fun delete(archsite: ArchSiteModel)
    fun findById(id:Long) : ArchSiteModel?
    fun clear()
}