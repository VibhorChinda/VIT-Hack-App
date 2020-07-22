package com.benrostudios.vithackapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.Developer
import kotlinx.android.synthetic.main.about_us_item.view.*

class TeamAdapter(private val devData: List<Developer>): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {


    private lateinit var mContext: Context
    class TeamViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name: TextView = v.about_us_dev_name
        val post: TextView = v.about_us_dev_post
        val github: ImageView = v.about_us_dev_github
        val linkedIn: ImageView = v.about_us_dev_linked_in
        val email:ImageView = v.about_us_email_id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.about_us_item,parent,false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = devData.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.name.text = devData[position].name
        holder.post.text = devData[position].post
        if(devData[position].githubLink.isBlank()){
            holder.github.setImageDrawable(mContext.getDrawable(R.drawable.ic_dribbble_circle_filled))
            holder.github.setOnClickListener {
                openLink(devData[position].dribbleLink)
            }
        }else{
            holder.github.setOnClickListener {
                openLink(devData[position].githubLink)
            }
        }
        if(devData[position].instagramLink.isBlank()){
            holder.email.setOnClickListener {
                openLink(devData[position].linkedInLink)
            }
        }else{
            holder.email.setImageDrawable(mContext.getDrawable(R.drawable.ic_instagram_filled))
            holder.email.setOnClickListener {
                openLink(devData[position].instagramLink)
            }
        }
        holder.linkedIn.setOnClickListener {
            openLink(devData[position].linkedInLink)
        }
    }
    private fun openLink(link: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        mContext.startActivity(intent)
    }
}