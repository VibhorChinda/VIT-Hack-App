package com.benrostudios.vithackapp.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.Speaker
import com.benrostudios.vithackapp.utils.imagePlaceholder
import com.benrostudios.vithackapp.utils.shortToaster
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.speaker_item.view.*


class SpeakerAdapter(private val speakersList: List<Speaker>) :
    RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder>() {


    class SpeakerViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var speakerImage = v.speaker_item_image
        var speakerName = v.speaker_item_name
        var speakerDesignation = v.speaker_item_deisgnation
        var speakerCompany = v.speaker_item_company
        var joinNowButtopn = v.speaker_item_youtube_join_button
    }

    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.speaker_item, parent, false)
        return SpeakerViewHolder(view)
    }

    override fun getItemCount(): Int = speakersList.size

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        holder.speakerName.text = speakersList[position].name
        holder.speakerCompany.text = speakersList[position].company
        holder.speakerDesignation.text = speakersList[position].designation
        holder.joinNowButtopn.setOnClickListener {
            try {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(speakersList[position].sessionUrl)
                    )
                )
            } catch (exception: ActivityNotFoundException) {
                mContext.shortToaster("Video Not Playable")
            }
        }
        val options: RequestOptions = RequestOptions()
            .circleCrop()
        Glide.with(mContext)
            .load(speakersList[position].imageUrl)
            .placeholder(mContext.imagePlaceholder())
            .apply(options)
            .into(holder.speakerImage)
    }
}