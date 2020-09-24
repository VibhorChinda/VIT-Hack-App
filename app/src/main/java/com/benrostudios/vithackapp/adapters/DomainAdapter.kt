package com.benrostudios.vithackapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.Domain
import com.benrostudios.vithackapp.utils.imagePlaceholder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.domain_item.view.*

class DomainAdapter(private val domainsList: List<Domain> , private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<DomainAdapter.DomainViewHolder>() {

    private lateinit var mContext: Context

    class DomainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val container: CardView = v.domain_container
        val image: ImageView = v.domain_list_image
        val title: TextView = v.domain_list_title
        val shortDesc: TextView = v.domain_list_short_desc
        val showMore: Button = v.domain_list_show_more_btn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DomainViewHolder {
        mContext = parent.context
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.domain_item, parent, false)
        return DomainViewHolder(v)
    }

    override fun getItemCount(): Int = domainsList.size

    override fun onBindViewHolder(holder: DomainViewHolder, position: Int) {
        holder.title.text = domainsList[position].domain
        holder.shortDesc.text = domainsList[position].description
        Glide.with(mContext)
            .load(domainsList[position].icon)
            .placeholder(mContext.imagePlaceholder())
            .into(holder.image)
        holder.container.setCardBackgroundColor(domainsList[position].colour.toColorInt())
        holder.showMore.setOnClickListener {
            onClick(position)
        }
    }
}