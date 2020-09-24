package com.benrostudios.vithackapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import kotlinx.android.synthetic.main.faq_item.view.*

class TracksAdapter(private val tracksList: List<String>, private val abbreviation: String): RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    private var counter = 0
    class TracksViewHolder(v: View): RecyclerView.ViewHolder(v){
        var title: TextView = v.faq_item_question
        var shortDesc: TextView = v.faq_item_answer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.faq_item,parent,false)
        return TracksViewHolder(v)
    }

    override fun getItemCount(): Int = tracksList.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.title.text = String.format("$abbreviation-PS-%02d", counter)
        holder.shortDesc.text = tracksList[position]
        counter++
    }
}