package com.benrostudios.vithackapp.ui.home.dynamicfaq

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.vithackapp.R

class DynamicFaq : Fragment() {

    companion object {
        fun newInstance() = DynamicFaq()
    }

    private lateinit var viewModel: DynamicFaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dynamic_faq_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DynamicFaqViewModel::class.java)
        // TODO: Use the ViewModel
    }

}