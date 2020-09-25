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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.Developer
import com.benrostudios.vithackapp.utils.imagePlaceholder
import com.benrostudios.vithackapp.utils.shortToaster
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.about_us_item.*
import kotlinx.android.synthetic.main.about_us_item.view.*

class TeamAdapter(private val devData: List<Developer>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {


    private lateinit var mContext: Context

    class TeamViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.about_us_dev_name
        val post: TextView = v.about_us_dev_post
        val github: ImageView = v.about_us_dev_github
        val linkedIn: ImageView = v.about_us_dev_linked_in
        val email: ImageView = v.about_us_email_id
        val devImage: ImageView = v.dev_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        mContext = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.about_us_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = devData.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference
        val devPictureTest = storageReference.child("Testing/speaker1.png")
        val options: RequestOptions = RequestOptions()
            .circleCrop()

        Glide.with(mContext)
            .load(devPictureTest)
            .placeholder(mContext.imagePlaceholder())
            .apply(options)
            .into(holder.devImage)

        holder.name.text = devData[position].name
        holder.post.text = devData[position].post
        if (devData[position].githubLink.isBlank()) {
            holder.github.setImageDrawable(mContext.getDrawable(R.drawable.ic_dribbble_circle_filled))
            holder.github.setOnClickListener {
                openLink(devData[position].dribbleLink)
            }
        } else {
            holder.github.setOnClickListener {
                openLink(devData[position].githubLink)
            }
        }
        if (devData[position].instagramLink.isBlank()) {
            holder.email.setOnClickListener {
                openEmail(devData[position].emailId)
            }
        } else {
            holder.email.setImageDrawable(mContext.getDrawable(R.drawable.ic_instagram_filled))
            holder.email.setOnClickListener {
                openEmail(devData[position].emailId)
            }
        }
        holder.linkedIn.setOnClickListener {
            openLink(devData[position].linkedInLink)
        }
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        mContext.startActivity(intent)
    }

    private fun openEmail(emailId: String){
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailId))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"VIT Hack 2020 Feedback")
        if (emailIntent.resolveActivity(mContext.packageManager) != null) {
            mContext.startActivity(emailIntent)
        }else{
           mContext.shortToaster("No Email apps found!")
        }
    }
}