package com.jk.historyatlas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jk.historyatlas.R
import com.jk.historyatlas.helpers.readImageFromPath
import com.jk.historyatlas.models.ArchSiteModel
import kotlinx.android.synthetic.main.card_archsite.view.*

interface ArchSiteListener {
    fun onArchSiteClick(archsite: ArchSiteModel)
}

class ArchSiteAdapter constructor(private var archsites: List<ArchSiteModel>, private val listener: ArchSiteListener) :
    RecyclerView.Adapter<ArchSiteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_archsite,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val archsite = archsites[holder.adapterPosition]
        holder.bind(archsite, listener)
    }

    override fun getItemCount(): Int = archsites.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(archsite: ArchSiteModel,  listener : ArchSiteListener) {
            itemView.archsiteTitle.text = archsite.title
            itemView.lat.text = archsite.location.lat.toString()
            itemView.lng.text = archsite.location.lng.toString()
            if (archsite.visited) {
                itemView.visited.setChecked(true)
            }
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, archsite.image))
            itemView.setOnClickListener { listener.onArchSiteClick(archsite) }
        }
    }
}
