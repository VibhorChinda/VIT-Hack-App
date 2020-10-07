package com.benrostudios.vithackapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.faq_item.view.*
import kotlinx.android.synthetic.main.faq_item.view.faq_item_answer
import kotlinx.android.synthetic.main.faq_item.view.faq_item_question
import kotlinx.android.synthetic.main.list_item_track.view.*

class TracksAdapter(private val tracksList: List<String>, private val abbreviation: String) :
    RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    private var counter = 0

    class TracksViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView = v.faq_item_question
        var shortDesc: TextView = v.faq_item_answer
        var showMoreButton: MaterialButton = v.btn_track_show_more
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_track, parent, false)
        return TracksViewHolder(v)
    }

    override fun getItemCount(): Int = tracksList.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val string = tracksList[position]
        val descriptionLink = string.split(regex = "~".toRegex())
        val link = descriptionLink[1]
        holder.title.text = String.format("$abbreviation-PS-%02d", counter)
        holder.shortDesc.text = descriptionLink[0]
        counter++
        if (link.isNotEmpty()) {
            holder.showMoreButton.visibility = View.VISIBLE
            holder.showMoreButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                holder.itemView.context.startActivity(intent)
            }
        } else {
            holder.showMoreButton.visibility = View.GONE
        }
    }
}