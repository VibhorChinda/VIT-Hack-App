package com.benrostudios.vithackapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.utils.imagePlaceholder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.company_item.view.*

class CompanyAdapter(private val companyItems: List<String>) :
    RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.company_item, parent, false)
        return CompanyViewHolder(view)
    }


    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference
        val companyLogo = storageReference.child(companyItems[position])
        Glide.with(mContext)
            .load(companyLogo)
            .placeholder(mContext.imagePlaceholder())
            .into(holder.companyImage)

    }

    override fun getItemCount(): Int = companyItems.size

    class CompanyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val companyImage: ImageView = v.company_image_item
    }
}