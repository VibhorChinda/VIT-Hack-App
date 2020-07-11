package com.benrostudios.vithackapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.FAQ
import kotlinx.android.synthetic.main.faq_item.view.*

class FaqAdapter(private var faqList: List<FAQ>): RecyclerView.Adapter<FaqAdapter.FaqViewHolder>(), Filterable {

    private var mFaqList: List<FAQ> =faqList

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

    override fun getFilter(): Filter {
        return customFilter
    }

    private var customFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val queryString = constraint?.toString()?.toLowerCase()

            val filterResults = Filter.FilterResults()
            filterResults.values = if (queryString==null || queryString.isEmpty())
                mFaqList
            else
                faqList.filter {
                    it.question.toLowerCase().contains(queryString) ||
                            it.answer.toLowerCase().contains(queryString)
                }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            faqList = results?.values as List<FAQ>
            notifyDataSetChanged()
        }

    }

}