package com.benrostudios.vithackapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.sax.TextElementListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.TimeLine
import com.benrostudios.vithackapp.utils.hide
import com.benrostudios.vithackapp.utils.show
import kotlinx.android.synthetic.main.timeline_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TimelineAdapter(private val timelineData: List<TimeLine>) :
    RecyclerView.Adapter<TimelineAdapter.TimeViewHolder>() {

    private lateinit var mContext: Context

    @SuppressLint("SimpleDateFormat")
    private var dateFormatter: SimpleDateFormat = SimpleDateFormat("hh:mm a")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        mContext = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_list_item, parent, false)
        return TimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timelineData.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.sessionDescription.text = timelineData[position].subtitle
        holder.sessionTitle.text = timelineData[position].title
        holder.sessionTime.text =
            dateFormatter.format(Date(timelineData[position].startUnix * 1000L))

        try{
            if (!isSameDay(timelineData[position].startUnix, timelineData[position + 1].startUnix)) {
                holder.dateHeader.show()
                holder.dateHeader.text = getDay(timelineData[position+1].startUnix)
            }else{
                holder.dateHeader.hide()
            }
        }catch(e: Exception){
            Log.d("Timeline Adapter","Final Card Reached")
        }
    }

    private fun getDay(epoch: Long): String {
        val fmt = SimpleDateFormat("dd MMMM yyyy")
        return fmt.format(Date(epoch * 1000L))
    }


    private fun isSameDay(date1: Long, date2: Long): Boolean {
        val fmt = SimpleDateFormat("yyyyMMdd")
        return fmt.format(Date(date1 * 1000L)) == fmt.format(Date(date2 * 1000L))
    }

    class TimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sessionTime: TextView = view.timeline_session_time
        val sessionTitle: TextView = view.timeline_session_title
        val sessionDescription: TextView = view.timeline_session_description
        val sessionIndicator: ImageView = view.timeline_session_completed_indicator
        val dateHeader: TextView = view.timeline_date_view
    }
}