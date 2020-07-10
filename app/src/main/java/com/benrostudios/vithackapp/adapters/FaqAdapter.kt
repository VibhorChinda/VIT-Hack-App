package com.benrostudios.vithackapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.FAQ
import kotlinx.android.synthetic.main.faq_item.view.*

class FaqAdapter(private val faqList: List<FAQ>): RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_item,parent,false)
        return FaqViewHolder(view)
    }

    override fun getItemCount(): Int = faqList.size

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.faqQuestion.text = faqList[position].question
        holder.faqAnswer.text = faqList[position].answer
    }

    class FaqViewHolder(v: View): RecyclerView.ViewHolder(v){
        var faqQuestion: TextView = v.faq_item_question
        var faqAnswer: TextView = v.faq_item_answer
    }
}